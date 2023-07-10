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
import dev.android.appbuses.database.api
import dev.android.appbuses.databinding.ActivitySeatBinding
import dev.android.appbuses.models.Asiento
import dev.android.appbuses.models.FormaPago
import dev.android.appbuses.models.Frecuencia
import dev.android.appbuses.models.Venta
import dev.android.appbuses.utils.Constants
import org.json.JSONArray
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class SeatActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySeatBinding
    private val adapter: SeatsAdapter by lazy{
        SeatsAdapter()
    }
    var total = 0f
    private var seatsPrices = mutableListOf<Asiento>()
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
<<<<<<< Updated upstream

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, FileActivity::class.java).apply {
                if (bundle != null) {
                    putExtras(bundle)
                    putExtra("total", total)
=======
        val option = bundle?.getString("amount")
        val payment = bundle?.getInt("payment")
        if (bundle != null) {
            email = bundle.getString("email").toString()
            getUser(email)
        }


        configuration = PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(this.clienteId)
        Toast.makeText(this@SeatActivity, payment.toString(), Toast.LENGTH_SHORT).show()

        binding.btnNext.setOnClickListener {
            if(payment == 3){
                getPayment(total.toString())
            }else{
                val passengers = adapter.getAllPassengers()
                var pass = true
                for (i in 0 until adapter.itemCount){
                    if (passengers[i] == "") {
                        pass = false
                        Toast.makeText(this@SeatActivity, "Llene todos los asientos", Toast.LENGTH_SHORT).show()
                        break
                    }
                }
                if (pass) {
                    val intent = Intent(this, FileActivity::class.java).apply {
                        if (bundle != null) {
                            putExtras(bundle)
                            putExtra("total", total)
                            putStringArrayListExtra("listaExtra", ArrayList(passengers))
                            putExtra("cantidad", adapter.seats.size)
                        }
                    }
                    startActivity(intent)
>>>>>>> Stashed changes
                }
            }
            startActivity(intent)
        }

        val option = bundle?.getString("amount")

        bundle?.let {
            val frequency = it.getSerializable(Constants.KEY_FREQUENCY) as Frecuencia
            if (option != null) {
                cargarDatos(frequency.id_bus, frequency.id_viaje, option.toInt())
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun cargarDatos(id_bus: Int, id_viaje: Int,  amount: Int){
        adapter.context = this@SeatActivity
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://nilotic-quart.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api::class.java)
        val retrofit = retrofitBuilder.getSeats(id_bus, id_viaje)
        retrofit.enqueue(
            object : Callback<List<Asiento>> {
                override fun onFailure(call: Call<List<Asiento>> , t: Throwable) {
                    Log.d("Agregar", t.message.toString())
                }
                override fun onResponse(call: Call<List<Asiento>> , response: retrofit2.Response<List<Asiento>> ) {
                    Log.d("Agregar", response.message().toString())
                    if (response.isSuccessful) {
                        val asientos = response.body()
                        seatsPrices = asientos as MutableList<Asiento>
                        if (asientos != null) {
                            val op = mutableListOf<String>()
                            adapter.seatType = asientos
                            Toast.makeText(this@SeatActivity, adapter.seatType.size.toString(), Toast.LENGTH_SHORT).show()
                            // Notificar al adaptador de cambios en los datos
                            adapter.notifyDataSetChanged()
                        }
                    } else {
                        // Manejar el caso de respuesta no exitosa
                        Toast.makeText(this@SeatActivity, "No existen elementos", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
        binding.rvFrequency.adapter = adapter
        binding.rvFrequency.layoutManager = LinearLayoutManager(this)
        binding.rvFrequency.setHasFixedSize(true)
        val seats = mutableListOf<Int>()
        for (i in 1..amount)
            seats.add(i)
        adapter.seats = seats

        var prices = mutableListOf<Int>()
        for (i in 0 until amount){
            prices.add(0)
        }

        adapter.getSpinnerOption = {
            prices = adapter.getAllSelectedOptions() as MutableList<Int>
            if(prices.size != 0){
                total = seatsPrices[0].costo_parada * seats.size
                for (i in 0 until seats.size){
                    for (j in 0 until seatsPrices.size){
                        if (prices[i] == seatsPrices.size - j){
                            total += seatsPrices[j].costo_adicional
                        }
                    }
                }
            }
            binding.txtTotal.text = "$" + String.format("%.2f", total)
        }
    }
}