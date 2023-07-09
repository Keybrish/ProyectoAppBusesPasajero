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
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import dev.android.appbuses.database.api
import dev.android.appbuses.databinding.ActivityMainBinding
import dev.android.appbuses.models.Asiento
import dev.android.appbuses.models.Frecuencia
import dev.android.appbuses.utils.Constants
import org.json.JSONArray
import org.json.JSONException
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.Executors
import org.apache.commons.text.StringEscapeUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val adapter: FrequencyAdapter by lazy{
        FrequencyAdapter()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        cargarDatos()

        binding.btnFilter.setOnClickListener {
                val intent = Intent(this, FilterMenuActivity::class.java).apply {
                }
                startActivity(intent)
        }

        binding.btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java).apply {
            }
            startActivity(intent)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun cargarDatos(){
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://nilotic-quart.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api::class.java)
        val retrofit = retrofitBuilder.frequencies
        retrofit.enqueue(
            object : Callback<List<Frecuencia>> {
                override fun onFailure(call: Call<List<Frecuencia>>, t: Throwable) {
                    Log.d("Agregar", t.message.toString())

                }
                override fun onResponse(call: Call<List<Frecuencia>>, response: retrofit2.Response<List<Frecuencia>> ) {
                    if (response.isSuccessful) {
                        val frecuencias = response.body()
                        Log.d("Respuesta", frecuencias.toString())
                        if (frecuencias != null) {
                            adapter.frecuencias = frecuencias
                            // Notificar al adaptador de cambios en los datos
                            adapter.notifyDataSetChanged()
                        }
                    } else {
                        // Manejar el caso de respuesta no exitosa
                        Toast.makeText(this@MainActivity, "No existen elementos", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        )
        binding.rvFrequency.adapter = adapter
        binding.rvFrequency.layoutManager = LinearLayoutManager(this)
        binding.rvFrequency.setHasFixedSize(true)

        val bund = intent.extras!!
        val email = bund?.getString("email")

        adapter.setOnClickListener = {
            val bundle = Bundle().apply {
                putSerializable(Constants.KEY_FREQUENCY, it)
            }
            val intent = Intent(this, BusDetailActivity()::class.java).apply {
                putExtras(bundle)
                putExtra("email", email)
                putExtra("option", "login")
            }
            startActivity(intent)
        }
    }
}