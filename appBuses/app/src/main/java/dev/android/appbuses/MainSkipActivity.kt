package dev.android.appbuses

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import dev.android.appbuses.database.api
import dev.android.appbuses.databinding.ActivityMainBinding
import dev.android.appbuses.databinding.ActivityMainSkipBinding
import dev.android.appbuses.models.Frecuencia
import dev.android.appbuses.models.Usuario
import dev.android.appbuses.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainSkipActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainSkipBinding
    private var email = ""
    private val adapter: FrequencyAdapter by lazy{
        FrequencyAdapter()
    }
    private var bund: Bundle? = null
    private var filtro: String? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainSkipBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        bund = intent.extras
        cargarDatos()

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

//        bund.getString("", "")
//        bund = intent.extras!!

        val sharedPreferences = getSharedPreferences("PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
        email = sharedPreferences.getString("email", "").toString()

        binding.btnFilter.setOnClickListener {
            val intent = Intent(this, FilterMenuActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }

        filtro = bund?.getString("filtro")

        binding.edtOrigin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (edtDestination.text.isNotEmpty()) {
                    cargarDatosFiltro("")
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.edtDestination.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (edtOrigin.text.isNotEmpty()) {
                    cargarDatosFiltro("")
                }

            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (edtOrigin.text.isEmpty() || edtDestination.text.isEmpty()) {
                    Toast.makeText(this@MainSkipActivity, "Llene primero el origen y destino", Toast.LENGTH_SHORT).show()
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
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
                        Toast.makeText(this@MainSkipActivity, "No existen elementos", Toast.LENGTH_SHORT).show()
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
                putExtra("option", "nologin")
            }
            startActivity(intent)
        }
    }

    fun cargarDatosFiltro(filter: String){
        if (edtOrigin.text.isNotEmpty() && edtDestination.text.isNotEmpty()){
            val retrofitBuilder = Retrofit.Builder()
                .baseUrl("https://nilotic-quart.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(api::class.java)
            var retrofit = retrofitBuilder.getFrequenciesOD(edtOrigin.text.toString(), edtDestination.text.toString())
            if(filter == "cooperativa"){
                retrofit = retrofitBuilder.getFrequenciesCooperative(edtOrigin.text.toString(), edtDestination.text.toString(), edtSearch.text.toString())
            } else if (filter == "asiento") {
                retrofit = retrofitBuilder.getFrequenciesSeat(edtOrigin.text.toString(), edtDestination.text.toString(), edtSearch.text.toString())
            } else if (filter == "chasis") {
                retrofit = retrofitBuilder.getFrequenciesChassis(edtOrigin.text.toString(), edtDestination.text.toString(), edtSearch.text.toString())
            } else if (filter == "carroceria") {
                retrofit = retrofitBuilder.getFrequenciesBodywork(edtOrigin.text.toString(), edtDestination.text.toString(), edtSearch.text.toString())
            } else if (filter == "directo") {
                retrofit = retrofitBuilder.getFrequenciesType(edtOrigin.text.toString(), edtDestination.text.toString(), 1)
            } else if (filter == "paradas"){
                retrofit = retrofitBuilder.getFrequenciesType(edtOrigin.text.toString(), edtDestination.text.toString(), 2)
            }
            retrofit.enqueue(
                object : Callback<List<Frecuencia>> {
                    override fun onFailure(call: Call<List<Frecuencia>>, t: Throwable) {
                        binding.rvFrequency.visibility = View.GONE
                        Log.d("Agregar", t.message.toString())

                    }
                    override fun onResponse(call: Call<List<Frecuencia>>, response: retrofit2.Response<List<Frecuencia>> ) {
                        binding.rvFrequency.visibility = View.VISIBLE
                        if (response.isSuccessful) {
                            if (response.toString().isEmpty()){
                                binding.rvFrequency.visibility = View.GONE
                            }
                            val frecuencias = response.body()
                            Log.d("Respuesta", frecuencias.toString())
                            if (frecuencias != null && frecuencias.isNotEmpty()) {
                                adapter.updateListFrequencies(frecuencias)
                                // Notificar al adaptador de cambios en los datos
                                adapter.notifyDataSetChanged()
                            }
                        } else {
                            // Manejar el caso de respuesta no exitosa
                            Toast.makeText(this@MainSkipActivity, "No existen elementos", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            )

            adapter.setOnClickListener = {
                val bundle = Bundle().apply {
                    putSerializable(Constants.KEY_FREQUENCY, it)
                }
                val intent = Intent(this, BusDetailActivity()::class.java).apply {
                    putExtras(bundle)
                    putExtra("option", "nologin")
                }
                startActivity(intent)
            }
        }
    }

    companion object {
        const val REQUEST_CODE = 1 // Código de solicitud, puedes elegir cualquier número entero
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val extraValue = data?.getStringExtra("filtro")
            // Hacer algo con el valor del extra
            if(edtOrigin.text.isNotEmpty() && edtDestination.text.isNotEmpty()) {
                if (extraValue == "directo"){
                    cargarDatosFiltro(extraValue)
                }
                if (extraValue == "paradas"){
                    cargarDatosFiltro(extraValue)
                }
            }
            binding.edtSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (extraValue != null) {
                        if(edtOrigin.text.isNotEmpty() && edtDestination.text.isNotEmpty()) {
                            cargarDatosFiltro(extraValue)
                        } else {
                            Toast.makeText(this@MainSkipActivity, "Primero llene origen y destino de viaje", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        cargarDatosFiltro("")
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRestart() {
        super.onRestart()
        filtro = bund?.getString("filtro")
        if (filtro != null){
//            Toast.makeText(this@MainActivity, filtro, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}