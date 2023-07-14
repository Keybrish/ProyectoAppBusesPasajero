package dev.android.appbuses

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import dev.android.appbuses.database.api
import dev.android.appbuses.databinding.ActivityProfileBinding
import dev.android.appbuses.models.Frecuencia
import dev.android.appbuses.models.Usuario
import dev.android.appbuses.models.Venta
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var user: Usuario
    private lateinit var bundle: Bundle
    private var email = ""
    private val progressDialog: ProgressDialog? = null
    private lateinit var storageReference: StorageReference
    private val storage_path = "comprobantes/*"
    private var photo = "pago"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        bundle = intent.extras!!
        storageReference = FirebaseStorage.getInstance().reference

        email = bundle?.getString("email").toString()
        Toast.makeText(this@ProfileActivity, email.toString(), Toast.LENGTH_SHORT).show()
        if (email != null) {
            getUser(email)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnLogout.setOnClickListener {
            cerrarSesion()
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        binding.btnEditInfo.setOnClickListener {
            val intent = Intent(this, ProfileInfoActivity::class.java).apply {
                putExtras(bundle)
            }
            startActivity(intent)
        }

        binding.btnInfo.setOnClickListener {
            val intent = Intent(this, ProfileInfoActivity::class.java).apply {
                putExtras(bundle)
            }
            startActivity(intent)
            finish()
        }

        binding.btnChangePassword.setOnClickListener {
            val intent = Intent(this, ChangePasswordActivity::class.java).apply {
                putExtras(bundle)
            }
            startActivity(intent)
        }

        binding.btnHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java).apply {
                putExtras(bundle)
            }
            startActivity(intent)
        }

        val loadImage =
            registerForActivityResult(ActivityResultContracts.GetContent(), ActivityResultCallback {
                binding.imgProfile.setImageURI(it)
                if (it != null) {
                    uploadImage(it)
                }
            })

        binding.btnCamera.setOnClickListener {
            loadImage.launch("image/*")
        }
    }

    fun cerrarSesion() {
        val preferencias: SharedPreferences.Editor =
            getSharedPreferences("PREFERENCE_FILE_KEY", Context.MODE_PRIVATE).edit()
        preferencias.clear()
        preferencias.apply()
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getUser(email_usuario: String) {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://nilotic-quart.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api::class.java)
        val retrofit = retrofitBuilder.getUser(email_usuario)
        retrofit.enqueue(
            object : Callback<Usuario> {
                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    Log.d("Agregar", t.message.toString())
                }

                override fun onResponse(
                    call: Call<Usuario>,
                    response: retrofit2.Response<Usuario>
                ) {
                    if (response.isSuccessful) {
                        val usuario = response.body()
                        Log.d("Respuesta", usuario.toString())
                        if (usuario != null) {
                            user = usuario
                            val name = user.nombre_usuario.split(" ")
                            val lastName = user.apellido_usuario.split(" ")
                            binding.txtName.text = name[0] + " " + lastName[0]
                            binding.txtFullName.text = user.nombre_usuario + " " + usuario.apellido_usuario
                            Picasso.get().load(usuario.foto_usuario)
                                .error(R.drawable.avatar)
                                .into(binding.imgProfile)
                        }
                    } else {
                        // Manejar el caso de respuesta no exitosa
                        Toast.makeText(
                            this@ProfileActivity,
                            "No existen elementos",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
            }
        )
    }

    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun uploadImage(imageUrl: Uri) {
        progressDialog?.setMessage("Actualizando foto")
        progressDialog?.show()

        val storagePath = "$storage_path $photo${System.currentTimeMillis()}"
        val reference: StorageReference = storageReference.child(storagePath)

        reference.putFile(imageUrl)
            .addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    val downloadUrl = uri.toString()
                        val usser = user
                        usser.foto_usuario = downloadUrl
                        updateUser(usser)
                }.addOnFailureListener { exception ->
                    showToast("Error al obtener la URL de descarga")
                    progressDialog?.dismiss()
                }
            }
            .addOnFailureListener { exception ->
                showToast("Error al cargar foto")
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@ProfileActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun updateUser(user: Usuario) {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://nilotic-quart.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api::class.java)
        val retrofit = retrofitBuilder.updateUser(user.id_usuario, user.email_usuario, user.nombre_usuario, user.apellido_usuario, user.telefono_usuario, user.foto_usuario)
        retrofit.enqueue(
            object : Callback<Usuario> {
                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    Log.d("Agregar", t.message.toString())
                }
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                    Log.d("Agregar", response.message().toString())
                }
            }
        )
    }
}