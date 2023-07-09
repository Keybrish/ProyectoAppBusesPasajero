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
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import dev.android.appbuses.database.api
import dev.android.appbuses.databinding.ActivityMainBinding
import dev.android.appbuses.models.Asiento
import dev.android.appbuses.models.Frecuencia
import dev.android.appbuses.models.Usuario
import dev.android.appbuses.utils.Constants
import kotlinx.android.synthetic.main.activity_filter_menu.view.*
import kotlinx.android.synthetic.main.activity_main.*
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
    private lateinit var user: Usuario
    private lateinit var bund: Bundle
    private var filtro: String? = null
    private var list: MutableList<Frecuencia> = ArrayList()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        cargarDatos()

        bund = intent.extras!!

        val sharedPreferences = getSharedPreferences("PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
        email = sharedPreferences.getString("email", "").toString()

        Toast.makeText(this@MainActivity, email.toString(), Toast.LENGTH_SHORT).show()
        //user = Usuario(8, "", "", "", "", "", "", "")
        if (email != null) {
            getUser(email)
        }

        binding.btnFilter.setOnClickListener {
            val intent = Intent(this, FilterMenuActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }

        binding.btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java).apply {
                putExtra("email", email)
            }
            startActivity(intent)
        }
        Toast.makeText(this, email, Toast.LENGTH_SHORT).show()
        filtro = bund.getString("filtro")

        binding.edtOrigin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (edtDestination.text.isNotEmpty()) {
                    cargarDatosFiltro("")
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.edtDestination.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (edtOrigin.text.isNotEmpty()) {
                    cargarDatosFiltro("")
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (edtOrigin.text.isEmpty() && edtDestination.text.isEmpty()) {
                    Toast.makeText(this@MainActivity, "Llene primero el origen y destino", Toast.LENGTH_SHORT).show()
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
                        Toast.makeText(this@MainActivity, "No existen elementos", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        )
        binding.rvFrequency.adapter = adapter
        binding.rvFrequency.layoutManager = LinearLayoutManager(this)
        binding.rvFrequency.setHasFixedSize(true)

        bund = intent.extras!!
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
                            Toast.makeText(this@MainActivity, "No existen elementos", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            )

            bund = intent.extras!!
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
                            Picasso.get().load(usuario.foto_usuario)
                                .error(R.drawable.avatar)
                                .into(binding.imgProfile)
                        }
                    } else {
                        // Manejar el caso de respuesta no exitosa
                        Toast.makeText(this@MainActivity, "No existen elementos", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
    }

    override fun onRestart() {
        super.onRestart()
        filtro = bund.getString("filtro")
        if (filtro != null){
            Toast.makeText(this@MainActivity, filtro, Toast.LENGTH_SHORT).show()
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
                            Toast.makeText(this@MainActivity, "Primero llene origen y destino de viaje", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        cargarDatosFiltro("")
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }

}