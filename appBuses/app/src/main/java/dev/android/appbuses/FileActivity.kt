package dev.android.appbuses

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import dev.android.appbuses.databinding.ActivityFileBinding
import java.util.*
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import dev.android.appbuses.database.api
import dev.android.appbuses.models.*
import dev.android.appbuses.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFileBinding
    private lateinit var storageReference: StorageReference
    private val storage_path = "comprobantes/*"
    var image: Uri? = null
    private var photo = "pago"
    private val progressDialog: ProgressDialog? = null
    private lateinit var bundle: Bundle
    private lateinit var user: Usuario
    private var email = ""
    private lateinit var freq: Frecuencia

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        storageReference = FirebaseStorage.getInstance().reference
        bundle = intent.extras!!

        email = bundle?.getString("email").toString()
        if (email != null) {
            getUser(email)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java).apply {
                putExtras(bundle)
            }
            startActivity(intent)
        }

        bundle?.let {
            freq = it.getSerializable(Constants.KEY_FREQUENCY) as Frecuencia
        }

        binding.btnNext.setOnClickListener {
            bundle?.let {
                val frequency = it.getSerializable(Constants.KEY_FREQUENCY) as Frecuencia

                if (image == null) {
                    Toast.makeText(this@FileActivity, "Agregue comprobante", Toast.LENGTH_SHORT).show()
                } else {
                    image?.let { it1 -> uploadImage(it1) }
                    val intent = Intent(this, PaymentSuccessfulActivity::class.java).apply {
                        putExtras(bundle)
                    }
                    startActivity(intent)
                }
            }
        }

        val loadFile = registerForActivityResult(ActivityResultContracts.GetContent(), ActivityResultCallback {
            binding.txtFile.text = it?.let { it1 -> getName(it1, applicationContext) }
            image = it
        })

        binding.btnUpload.setOnClickListener {
            loadFile.launch("image/*")
        }
    }

    @SuppressLint("Range")
    fun getName(uri: Uri, context: Context): String? {
        var result: String? = null
        if (uri != null) {
            if (uri.scheme.equals("content")) {
                val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
                try {
                    if (cursor != null && cursor.moveToFirst()) {
                        result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    }
                }finally {
                    cursor?.close()
                }
                if(result == null) {
                    result = uri.path.toString()
                    val cutt = result.lastIndexOf('/')
                    if(cutt != -1) {
                        result = result.substring(cutt +1)
                    }
                }
            }
        }
        return result
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
                    val payment = bundle?.getInt("payment")
                    val total = bundle?.getFloat("total")

                    val sale = payment?.let {
                        Venta(user.id_usuario, freq.id_viaje, freq.id_parada, freq.fecha_viaje,
                            it,total, "12345", downloadUrl)
                    }
                    Toast.makeText(applicationContext, sale.toString(), Toast.LENGTH_SHORT).show()
                    Log.d("sale", sale.toString())

                    if (sale != null) {
                        addSale(sale)
                    }
                }.addOnFailureListener {
                    showToast("Error al obtener la URL de descarga")
                    progressDialog?.dismiss()
                }
            }
            .addOnFailureListener { exception ->
                showToast("Error al cargar foto")
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@FileActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun addSale(venta: Venta) {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://nilotic-quart.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api::class.java)
        Log.d("venta", venta.toString())
        val retrofit = retrofitBuilder.insertData(user.id_usuario, freq.id_viaje, freq.id_parada, freq.fecha_viaje, venta.id_forma_pago, 0,
            venta.total_venta, "", "ee")
        retrofit.enqueue(
            object : Callback<Venta> {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onFailure(call: Call<Venta>, t: Throwable) {
                    Log.d("Agregar", "Error al agregar cliente")
                }
                override fun onResponse(call: Call<Venta>, response: retrofit2.Response<Venta>) {
                    Log.d("Agregar", "Cliente agregado con éxito")

                }
            }
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getUser(email_usuario: String){
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
                override fun onResponse(call: Call<Usuario>, response: retrofit2.Response<Usuario> ) {
                    if (response.isSuccessful) {
                        val usuario = response.body()
                        Log.d("Respuesta", usuario.toString())
                        if (usuario != null) {
                            user = usuario
                            Picasso.get().load(usuario.foto_usuario)
                                .error(R.drawable.avatar)
                                .into(binding.imgProfile)
                        }
                    } else {
                        // Manejar el caso de respuesta no exitosa
                        Toast.makeText(this@FileActivity, "No existen elementos", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRestart() {
        super.onRestart()
        getUser(email)
    }

}