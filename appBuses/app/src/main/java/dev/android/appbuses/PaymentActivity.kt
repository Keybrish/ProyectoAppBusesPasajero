package dev.android.appbuses

import android.R
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import dev.android.appbuses.database.api
import dev.android.appbuses.databinding.ActivityPaymentBinding
import dev.android.appbuses.models.FormaPago
import dev.android.appbuses.models.Frecuencia
import dev.android.appbuses.models.Usuario
import dev.android.appbuses.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPaymentBinding
    private var payments = mutableListOf<FormaPago>()
    private lateinit var bundle: Bundle
    private lateinit var user: Usuario
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

//        binding.spnPayment.dropDownVerticalOffset
//        val op = listOf("Paypal", "Deposito", "Transferencia")
//        val sp = ArrayAdapter(this, R.layout.simple_spinner_item, op)
//        binding.spnPayment.adapter = sp

        cargarDatos()
        bundle = intent.extras!!
        val email = bundle?.getString("email")
        Toast.makeText(this@PaymentActivity, email.toString(), Toast.LENGTH_SHORT).show()
        //user = Usuario(8, "", "", "", "", "", "", "")
        if (email != null) {
            getUser(email)
        }

        var isDefaultOptionSelected = true
        var payment = 1

        binding.spnPayment.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (isDefaultOptionSelected) {
                    isDefaultOptionSelected = false
                    return
                }

                val selectedItem = binding.spnPayment.selectedItem

                    for (pay in payments){
                        if(selectedItem == pay.forma_pago){
                            payment = pay.id_forma_pago
                        }
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Manejar el caso en que no se haya seleccionado nada
            }
        }

        binding.btnLess.setOnClickListener {
            if(binding.txtAmount.text.toString().toInt() > 1)
                binding.txtAmount.text = (binding.txtAmount.text.toString().toInt() - 1).toString()
        }

        binding.btnPlus.setOnClickListener {
            binding.txtAmount.text = (binding.txtAmount.text.toString().toInt() + 1).toString()
        }

        binding.btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java).apply {
            }
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        val bundle = intent.extras

        binding.btnNext.setOnClickListener {
            val amount = binding.txtAmount.text.toString()
            val intent = Intent(this, SeatActivity::class.java).apply {
                if (bundle != null) {
                    putExtras(bundle)
                }
                putExtra("amount", amount)
                putExtra("payment", payment)
            }
            startActivity(intent)
        }

        bundle?.let {
            val frequency = it.getSerializable(Constants.KEY_FREQUENCY) as Frecuencia

            binding.txtCooperative.text = frequency.nombre_cooperativa
            binding.txtOrigin.text = frequency.origen
            binding.txtDestination.text = frequency.destino
            binding.txtDate.text = frequency.fecha_viaje
            binding.txtTime.text = frequency.hora_salida_viaje.substring(0,5) + " - " + frequency.hora_llegada_viaje.substring(0,5)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun cargarDatos(){
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://nilotic-quart.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api::class.java)
        val retrofit = retrofitBuilder.getPayments()
        retrofit.enqueue(
            object : Callback<List<FormaPago>> {
                override fun onFailure(call: Call<List<FormaPago>>, t: Throwable) {
                    Log.d("Agregar", t.message.toString())
                }
                override fun onResponse(call: Call<List<FormaPago>>, response: retrofit2.Response<List<FormaPago>> ) {
                    if (response.isSuccessful) {
                        val formasPago = response.body()
                        payments = formasPago as MutableList<FormaPago>
                        Log.d("Respuesta", formasPago.toString())
                        if (formasPago != null) {
                            binding.spnPayment.dropDownVerticalOffset
                            val op = mutableListOf<String>()
                            for (element in formasPago){
                                op.add(element.forma_pago)
                            }
                            val sp = ArrayAdapter(this@PaymentActivity, R.layout.simple_spinner_item, op)
                            binding.spnPayment.adapter = sp
                        }
                    } else {
                        // Manejar el caso de respuesta no exitosa
                        Toast.makeText(this@PaymentActivity, "No existen elementos", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        )
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
                            binding.txtName.text = user.nombre_usuario + " " + user.apellido_usuario
                            binding.txtID.text = user.cedula_usuario
                        }
                    } else {
                        // Manejar el caso de respuesta no exitosa
                        Toast.makeText(this@PaymentActivity, "No existen elementos", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        )
    }
}