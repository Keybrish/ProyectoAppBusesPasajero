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
import com.squareup.picasso.Picasso
import dev.android.appbuses.databinding.ActivitySeatBinding
import dev.android.appbuses.models.Frecuencia
import dev.android.appbuses.utils.Constants
import org.json.JSONArray
import org.json.JSONException
import java.nio.charset.StandardCharsets
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class SeatActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySeatBinding
    private val adapter: SeatsAdapter by lazy{
        SeatsAdapter()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeatBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java).apply {
            }
            startActivity(intent)
        }

        val bundle = intent.extras

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, FileActivity::class.java).apply {
                if (bundle != null) {
                    putExtras(bundle)
                }
            }
            startActivity(intent)
        }

        val option = bundle?.getString("amount")

        bundle?.let {
            val frequency = it.getSerializable(Constants.KEY_FREQUENCY) as Frecuencia
            if (option != null) {
                cargarDatos(frequency.id_bus, option.toInt())
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun parseJson(json: String): List<String> {
        val seats = mutableListOf<String>()
        try {
            val jsonArray = JSONArray(json)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val tipo = jsonObject.getString("descripcion_asiento")
                seats.add(tipo)
            }
        } catch (e: JSONException) {
            Log.e("JSON parse error", e.toString())
        }
        return seats
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun cargarDatos(id_bus: Int, amount: Int){
        val queue = Volley.newRequestQueue(this)
        val url = "https://nilotic-quart.000webhostapp.com/listarTipoAsientosBus.php?id_bus_pertenece=$id_bus"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show()
                val seatsType = parseJson(response)
                adapter.seatType.add(seatsType.get(0))
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
        val seats = mutableListOf<Int>()
        for (i in 1..amount)
            seats.add(i)
        adapter.seats = seats
    }
}