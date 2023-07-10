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
import dev.android.appbuses.models.Frecuencia
import dev.android.appbuses.models.Usuario
import dev.android.appbuses.models.Venta
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
<<<<<<< Updated upstream
=======
    private var email = ""
    private lateinit var freq: Frecuencia
    private lateinit var purchase: Compra
>>>>>>> Stashed changes

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        storageReference = FirebaseStorage.getInstance().reference
        bundle = intent.extras!!

        val email = bundle?.getString("email")
        Toast.makeText(this@FileActivity, email.toString(), Toast.LENGTH_SHORT).show()
        //user = Usuario(8, "", "", "", "", "", "", "")
        if (email != null) {
            getUser(email)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java).apply {
            }
            startActivity(intent)
        }

        val payment = bundle?.getInt("payment")
        val total = bundle?.getFloat("total")

        binding.btnNext.setOnClickListener {
            bundle?.let {
                val frequency = it.getSerializable(Constants.KEY_FREQUENCY) as Frecuencia

                image?.let { it1 -> uploadImage(it1, frequency) }
//                val sale = payment?.let { it1 ->
//                    Venta(8, frequency.id_viaje, frequency.id_parada, frequency.fecha_viaje,
//                        it1,total, "12345", "")
//                }
//                if (sale != null) {
//                    addSale(sale)
//                }
            }

            val intent = Intent(this, PaymentSuccessfulActivity::class.java).apply {
            }
            startActivity(intent)
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
    private fun uploadImage(imageUrl: Uri, frequency: Frecuencia) {
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
                        Venta(user.id_usuario, frequency.id_viaje, frequency.id_parada, frequency.fecha_viaje,
                            it,total, "12345", downloadUrl)
                    }
                    if (sale != null) {
                        addSale(sale)
                    }
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
        Toast.makeText(this@FileActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun addSale(venta: Venta) {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://nilotic-quart.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api::class.java)
<<<<<<< Updated upstream
        val retrofit = retrofitBuilder.insertData(venta.id_comprador, venta.id_viaje_pertenece, venta.id_parada_pertenece, venta.fecha_venta, venta.id_forma_pago,venta.total_venta, "123455555", venta.comprobante)
=======
        Log.d("venta", venta.toString())
        val retrofit = retrofitBuilder.insertData(user.id_usuario, freq.id_viaje, freq.id_parada, freq.fecha_viaje, venta.id_forma_pago, 0,
            venta.total_venta, "", venta.comprobante)
        retrofit.enqueue(
            object : Callback<Venta> {
                override fun onFailure(call: Call<Venta>, t: Throwable) {
                    Log.d("Agregar", "Error al agregar cliente")
                }
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(call: Call<Venta>, response: retrofit2.Response<Venta>) {
                    Log.d("Agregar", "Cliente agregado con éxito")
                    getPurchase(user.id_usuario)
                }
            }
        )
    }

    private fun addSaleDatail(idNumber: String) {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://nilotic-quart.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api::class.java)
        val retrofit = retrofitBuilder.insertDataDetail(purchase.id_venta, 1, 0f, 0f, idNumber)
        retrofit.enqueue(
            object : Callback<Compra_Detalle> {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onFailure(call: Call<Compra_Detalle>, t: Throwable) {
                    Log.d("Agregar", "Error al agregar cliente")
                }
                override fun onResponse(call: Call<Compra_Detalle>, response: retrofit2.Response<Compra_Detalle>) {
                    Log.d("Añadido", "Cliente agregado con éxito")
                }
            }
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getPurchase(id_comprador: Int){
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://nilotic-quart.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api::class.java)
        val retrofit = retrofitBuilder.getLastPurchase(id_comprador)
        retrofit.enqueue(
            object : Callback<Compra> {
                override fun onFailure(call: Call<Compra>, t: Throwable) {
                    Log.d("Agregar", t.message.toString())
                }
                override fun onResponse(call: Call<Compra>, response: retrofit2.Response<Compra> ) {
                    if (response.isSuccessful) {
                        val compra = response.body()
                        if (compra != null) {
                            purchase = compra

                            val listaExtra = bundle?.getStringArrayList("listaExtra")
                            val passAmount = bundle.getInt("cantidad")
                            Log.d("Size", listaExtra.toString())
                            for (i in 0 until passAmount){
                                addSaleDatail(listaExtra!![i].toString())
                            }
                            updatePurchase(purchase.id_venta)
                        }
                    } else {
                        // Manejar el caso de respuesta no exitosa
                        Toast.makeText(this@FileActivity, "No existen elementos", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        )
    }

    private fun updatePurchase(id_venta: Int) {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://nilotic-quart.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api::class.java)
        val retrofit = retrofitBuilder.updatePurchase(id_venta, id_venta.toString())
>>>>>>> Stashed changes
        retrofit.enqueue(
            object : Callback<Venta> {
                override fun onFailure(call: Call<Venta>, t: Throwable) {
                    Log.d("Agregar", "Error al agregar cliente")
                }

                override fun onResponse(call: Call<Venta>, response: retrofit2.Response<Venta>) {
<<<<<<< Updated upstream
                    Log.d("Agregar", "Cliente agregado con éxito")
=======
                    Log.d("Añadido", "Comprobante editado")
>>>>>>> Stashed changes
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
                        }
                    } else {
                        // Manejar el caso de respuesta no exitosa
                        Toast.makeText(this@FileActivity, "No existen elementos", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        )
    }

}