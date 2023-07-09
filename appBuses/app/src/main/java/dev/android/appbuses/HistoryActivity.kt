package dev.android.appbuses

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import dev.android.appbuses.database.api
import dev.android.appbuses.databinding.ActivityHistoryBinding
import dev.android.appbuses.models.Compra
import dev.android.appbuses.models.Usuario
import dev.android.appbuses.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHistoryBinding
    private val adapter: HistoryAdapter by lazy{
        HistoryAdapter()
    }
    private lateinit var user: Usuario
    private lateinit var bundle: Bundle
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        bundle = intent.extras!!
        val email = bundle.getString("email")
        if (email != null) {
            getUser(email)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.textView30.setOnClickListener {
            val intent = Intent(this, HistoryQRActivity::class.java).apply {
            }
            startActivity(intent)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun cargarDatos(id_comprador: Int){
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://nilotic-quart.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api::class.java)
        val retrofit = retrofitBuilder.getPurchases(id_comprador)
        retrofit.enqueue(
            object : Callback<List<Compra>> {
                override fun onFailure(call: Call<List<Compra>>, t: Throwable) {
                    Log.d("Agregar", t.message.toString())
                }
                override fun onResponse(call: Call<List<Compra>>, response: retrofit2.Response<List<Compra>> ) {
                    if (response.isSuccessful) {
                        val compras = response.body()
                        Log.d("Respuesta", compras.toString())
                        if (compras != null) {
                            adapter.compras = compras
                            // Notificar al adaptador de cambios en los datos
                            adapter.notifyDataSetChanged()
                        }
                    } else {
                        Log.d("Error", response.body().toString())
                        // Manejar el caso de respuesta no exitosa
                        Toast.makeText(this@HistoryActivity, "No existen elementos", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        )
        binding.rvHistory.adapter = adapter
        binding.rvHistory.layoutManager = LinearLayoutManager(this)
        binding.rvHistory.setHasFixedSize(true)

        adapter.setOnClickListener = {
            val bundle = Bundle().apply {
                putSerializable(Constants.KEY_PURCHASE, it)
            }
            val intent = Intent(this, HistoryQRActivity()::class.java).apply {
                putExtras(bundle)
            }
            startActivity(intent)
        }
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
                            cargarDatos(user.id_usuario)
                        }
                    } else {
                        // Manejar el caso de respuesta no exitosa
                        Toast.makeText(
                            this@HistoryActivity,
                            "No existen elementos",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
            }
        )
    }
}