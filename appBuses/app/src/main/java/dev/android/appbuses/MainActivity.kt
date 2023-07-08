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
import dev.android.appbuses.databinding.ActivityMainBinding
import dev.android.appbuses.models.Frecuencia
import dev.android.appbuses.utils.Constants
import org.json.JSONArray
import org.json.JSONException
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.Executors
import org.apache.commons.text.StringEscapeUtils
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
    private fun parseJson(json: String): List<Frecuencia> {
        val frecuencias = mutableListOf<Frecuencia>()
        try {
            val jsonArray = JSONArray(json)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val id = jsonObject.getInt("id_frecuencia")
                val id_cooperativa = jsonObject.getInt("id_cooperativa_pertenece")
                val nombre_cooperativa = jsonObject.getString("nombre_cooperativa")
                val fotografia = jsonObject.getString("fotografia")
                val origen_frecuencia = jsonObject.getString("origen")
                val destino_frecuencia = jsonObject.getString("destino")
                val destino_provincia_frecuencia = jsonObject.getString("destinoProvincia")
                val duracion_frecuencia = LocalTime.parse(jsonObject.getString("duracion_frecuencia"), DateTimeFormatter.ofPattern("HH:mm:ss"))
                val tipo_frecuencia = jsonObject.getInt("tipo_frecuencia")
                val costo_frecuencia = jsonObject.getString("costo_frecuencia").toFloat()
                val estado_frecuencia = jsonObject.getInt("estado_frecuencia")
                val fecha_viaje = jsonObject.getString("fecha_viaje")
                val hora_salida = jsonObject.getString("hora_salida_viaje")
                val hora_llegada = jsonObject.getString("hora_llegada_viaje")
                val id_bus = jsonObject.getInt("id_bus")
                val numero_bus = jsonObject.getString("numero_bus")
                val placa_bus = jsonObject.getString("placa_bus")
                val chasis_bus = jsonObject.getString("chasis_bus")
                val carroceria_bus = jsonObject.getString("carroceria_bus")
                val frecuencia = Frecuencia(id,id_cooperativa,nombre_cooperativa,fotografia, origen_frecuencia, destino_frecuencia, destino_provincia_frecuencia, duracion_frecuencia, tipo_frecuencia, estado_frecuencia, fecha_viaje, hora_salida, hora_llegada, id_bus, numero_bus, placa_bus, chasis_bus, carroceria_bus, costo_frecuencia)
                frecuencias.add(frecuencia)
            }
        } catch (e: JSONException) {
            Log.e("JSON parse error", e.toString())
        }
        return frecuencias
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun cargarDatos(){
        //val sharedPref = requireActivity().getSharedPreferences("login_data", Context.MODE_PRIVATE)
        //val id = sharedPref.getString("id_cli", "")
        val queue = Volley.newRequestQueue(this)
        val url = "https://nilotic-quart.000webhostapp.com/listarViajesDiarios.php"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                val decodedResponse = String(response.toByteArray(), StandardCharsets.UTF_8)
                val frecuencias = parseJson(decodedResponse)
                adapter.frecuencias = frecuencias
                adapter.notifyDataSetChanged()
            },
            Response.ErrorListener { error ->
                Log.e("Volley error", error.toString())
                Toast.makeText(this, "Error al cargar las frecuencias", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(stringRequest)
        binding.rvFrequency.adapter = adapter
        binding.rvFrequency.layoutManager = LinearLayoutManager(this)
        binding.rvFrequency.setHasFixedSize(true)
        adapter.setOnclickDetail = {
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