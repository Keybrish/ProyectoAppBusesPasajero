package dev.android.appbuses

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.squareup.picasso.Picasso
import dev.android.appbuses.database.api
import dev.android.appbuses.databinding.ActivityProfileBinding
import dev.android.appbuses.models.Usuario
import dev.android.appbuses.models.Venta
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var user: Usuario
    private lateinit var bundle: Bundle

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        bundle = intent.extras!!
        val email = bundle?.getString("email")
        Toast.makeText(this@ProfileActivity, email.toString(), Toast.LENGTH_SHORT).show()
        if (email != null) {
            getUser(email)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnLogout.setOnClickListener {
            cerrarSesion()
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
        }

        binding.btnChangePassword.setOnClickListener {
            val intent = Intent(this, ChangePasswordActivity::class.java).apply {
            }
            startActivity(intent)
        }

        binding.btnHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java).apply {
            }
            startActivity(intent)
        }

        val loadImage =
            registerForActivityResult(ActivityResultContracts.GetContent(), ActivityResultCallback {
                binding.imgProfile.setImageURI(it)
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
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
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
                            binding.txtName.text = user.nombre_usuario + " " + user.apellido_usuario
                            binding.txtID.text = user.cedula_usuario
                            Picasso.get().load(usuario.foto_usuario)
                                .error(R.drawable.purple_aesthetic_woman_portrait_7h0yo10dvp884ons)
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
}