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
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.storage.FirebaseStorage
import dev.android.appbuses.databinding.ActivityFileBinding
import java.util.*
import com.google.firebase.storage.StorageReference
import dev.android.appbuses.database.api
import dev.android.appbuses.models.Frecuencia
import dev.android.appbuses.models.Venta
import dev.android.appbuses.utils.Constants
import org.json.JSONException
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        storageReference = FirebaseStorage.getInstance().reference
        val bundle = intent.extras

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java).apply {
            }
            startActivity(intent)
        }

        binding.btnNext.setOnClickListener {
            bundle?.let {
                val frequency = it.getSerializable(Constants.KEY_FREQUENCY) as Frecuencia

//                image?.let { it1 -> uploadImage(it1, frequency) }
                val sale = Venta(8, 1, 1, "2023-06-04", 1,123.50f, "12345", "")
                addSale(sale)
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
                    val sale = Venta(8, 1, 1, "2023-06-04", 1,123.50f, "12345", downloadUrl)
                    addSale(sale)
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
        val retrofit = retrofitBuilder.insertData(8, 1, 1, "2023-06-04", 1,123.50f, "12345", "downloadUrl")
        retrofit.enqueue(
            object : Callback<Venta> {
                override fun onFailure(call: Call<Venta>, t: Throwable) {
                    Log.d("Agregar", "Error al agregar cliente")

                }

                override fun onResponse(call: Call<Venta>, response: retrofit2.Response<Venta>) {
                    Log.d("Agregar", "Cliente agregado con éxito")

                }
            }
        )
    }

}