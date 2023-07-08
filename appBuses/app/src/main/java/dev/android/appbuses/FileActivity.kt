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
import dev.android.appbuses.models.Frecuencia
import dev.android.appbuses.models.Venta
import dev.android.appbuses.utils.Constants
import org.json.JSONException

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

                image?.let { it1 -> uploadImage(it1, frequency) }
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
                    val sale = Venta(8, 1, 1, frequency.fecha_viaje, 1, 1,123.50f, "12345", downloadUrl)
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

    fun addSale(venta: Venta) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://nilotic-quart.000webhostapp.com/generarVenta.php"

        val stringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener<String> { response ->
                try {
                    Toast.makeText(this, "Venta agregada con éxito", Toast.LENGTH_SHORT).show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Error al parsear la respuesta", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
                Toast.makeText(this, "Error de conexión", Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["id_comprador"] = venta.id_comprador.toString()
                params["id_viaje_pertenece"] = venta.id_viaje_pertenece.toString()
                params["id_parada_pertenece"] = venta.id_parada_pertenece.toString()
                params["fecha_venta"] = venta.fecha_venta
                params["id_forma_pago"] = venta.id_forma_pago.toString()
                params["estado_venta"] = venta.id_forma_pago.toString()
                params["total_venta"] = venta.total_venta.toString()
                params["codigo_qr_venta"] = venta.codigo_qr_venta
                params["comprobante_venta"] = venta.comprobante
                return params
            }
        }
        queue.add(stringRequest)
    }
}