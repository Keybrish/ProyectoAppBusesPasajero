package dev.android.appbuses

import android.content.Context
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
    private lateinit var email: String
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
        val sharedPreferences = getSharedPreferences("PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
        email = sharedPreferences.getString("email", "").toString()
        Toast.makeText(this, email, Toast.LENGTH_SHORT).show()
    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun parseJson(json: String): List<Frecuencia> {
//        val frecuencias = mutableListOf<Frecuencia>()
//        try {
//            val jsonArray = JSONArray(json)
//            for (i in 0 until jsonArray.length()) {
//                val jsonObject = jsonArray.getJSONObject(i)
//                val id = jsonObject.getInt("id_parada")
////                val id_cooperativa = jsonObject.getInt("id_cooperativa_pertenece")
//                val nombre_cooperativa = jsonObject.getString("nombre_cooperativa")
//                val fotografia = jsonObject.getString("fotografia")
//                val origen_frecuencia = jsonObject.getString("origen")
//                val destino_frecuencia = jsonObject.getString("destino")
//                val destino_provincia_frecuencia = jsonObject.getString("destinoProvincia")
////                val duracion_frecuencia = LocalTime.parse(jsonObject.getString("duracion_frecuencia"), DateTimeFormatter.ofPattern("HH:mm:ss"))
////                val tipo_frecuencia = jsonObject.getInt("tipo_frecuencia")
//                val duracion_frecuencia = jsonObject.getString("duracion_frecuencia")
//                val costo_frecuencia = jsonObject.getString("costo_parada").toFloat()
//                val estado_frecuencia = jsonObject.getInt("estado")
//                val fecha_viaje = jsonObject.getString("fecha_viaje")
//                val hora_salida = jsonObject.getString("hora_salida_viaje")
//                val hora_llegada = jsonObject.getString("hora_llegada_viaje")
//                val id_bus = jsonObject.getInt("id_bus")
//                val numero_bus = jsonObject.getString("numero_bus")
//                val placa_bus = jsonObject.getString("placa_bus")
//                val chasis_bus = jsonObject.getString("chasis_bus")
//                val carroceria_bus = jsonObject.getString("carroceria_bus")
//                val frecuencia = Frecuencia(id,nombre_cooperativa,fotografia, origen_frecuencia, destino_frecuencia, destino_provincia_frecuencia, duracion_frecuencia, estado_frecuencia, fecha_viaje, hora_salida, hora_llegada, id_bus, numero_bus, placa_bus, chasis_bus, carroceria_bus, costo_frecuencia)
//                frecuencias.add(frecuencia)
//            }
//        } catch (e: JSONException) {
//            Log.e("JSON parse error", e.toString())
//        }
//        return frecuencias
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun cargarDatos(){
        //val sharedPref = requireActivity().getSharedPreferences("login_data", Context.MODE_PRIVATE)
        //val id = sharedPref.getString("id_cli", "")
//        val queue = Volley.newRequestQueue(this)
//        val url = "https://nilotic-quart.000webhostapp.com/listarViajesDiarios.php"
//        val stringRequest = StringRequest(
//            Request.Method.GET, url,
//            Response.Listener<String> { response ->
//                val decodedResponse = String(response.toByteArray(), StandardCharsets.UTF_8)
//                val frecuencias = parseJson(decodedResponse)
//                adapter.frecuencias = frecuencias
//                adapter.notifyDataSetChanged()
//            },
//            Response.ErrorListener { error ->
//                Log.e("Volley error", error.toString())
//                Toast.makeText(this, "Error al cargar las frecuencias", Toast.LENGTH_SHORT).show()
//            }
//        )
//        queue.add(stringRequest)

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

        adapter.setOnClickListener = {
            val bundle = Bundle().apply {
                putSerializable(Constants.KEY_FREQUENCY, it)
            }
            val intent = Intent(this, BusDetailActivity()::class.java).apply {
                putExtras(bundle)
                putExtra("option", "login")
            }
            startActivity(intent)
        }
    }
}