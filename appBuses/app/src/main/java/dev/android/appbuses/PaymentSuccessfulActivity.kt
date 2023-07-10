package dev.android.appbuses

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.squareup.picasso.Picasso
import dev.android.appbuses.database.api
import dev.android.appbuses.databinding.ActivityPaymentSuccessfulBinding
import dev.android.appbuses.models.Compra
import dev.android.appbuses.models.Compra_Detalle
import dev.android.appbuses.models.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PaymentSuccessfulActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPaymentSuccessfulBinding
    private lateinit var bundle: Bundle
    private lateinit var user: Usuario
    private lateinit var purchase: Compra

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentSuccessfulBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        bundle = intent.extras!!

        val email = bundle.getString("email")

        if (email != null) {
            getUser(email)
        }

        binding.btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java).apply {
                putExtras(bundle)
            }
            startActivity(intent)
        }

        binding.btnBuy.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java).apply {
                putExtras(bundle)
            }
            startActivity(intent)
        }
    }

    private fun addSaleDatail(idNumber: String) {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://nilotic-quart.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api::class.java)
        val retrofit = retrofitBuilder.insertDataDetail(purchase?.id_venta, 1, 0f, 0f, idNumber)
        retrofit.enqueue(
            object : Callback<Compra_Detalle> {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onFailure(call: Call<Compra_Detalle>, t: Throwable) {
                    Log.d("Agregar", "Error al agregar cliente")
                }
                override fun onResponse(call: Call<Compra_Detalle>, response: retrofit2.Response<Compra_Detalle>) {
                    Log.d("Agregar", "Cliente agregado con éxito")

                }
            }
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getPurchase(id_comprador: Int){
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://nilotic-quart.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api::class.java)
        val retrofit = retrofitBuilder.getLastPurchase(id_comprador)
        retrofit.enqueue(
            object : Callback<Compra> {
                override fun onFailure(call: Call<Compra>, t: Throwable) {

                    Log.d("Agregar", t.message.toString())
                }
                override fun onResponse(call: Call<Compra>, response: retrofit2.Response<Compra> ) {
//                    Toast.makeText(applicationContext, response.body().toString(), Toast.LENGTH_SHORT).show()

                    if (response.isSuccessful) {
                        val compra = response.body()
                        if (compra != null) {
                            purchase = compra
                             Toast.makeText(applicationContext, purchase.id_venta.toString(), Toast.LENGTH_SHORT).show()

                                val listaExtra = bundle?.getStringArrayList("listaExtra")
                                val passAmount = bundle.getInt("cantidad")
//                    Toast.makeText(this@FileActivity, listaExtra.toString(), Toast.LENGTH_SHORT).show()
//                    Toast.makeText(this@FileActivity, passAmount.toString(), Toast.LENGTH_SHORT).show()
                                Log.d("Size", listaExtra?.size.toString())
                                for (i in 0 until passAmount){
                                    listaExtra?.get(i)?.let { addSaleDatail(it) }
                            }
                        }
                    } else {
                        // Manejar el caso de respuesta no exitosa
                        Toast.makeText(this@PaymentSuccessfulActivity, "No existen elementos", Toast.LENGTH_SHORT).show()
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
                                Picasso.get().load(usuario.foto_usuario)
                                    .error(R.drawable.avatar)
                                    .into(binding.imgProfile)

                                getPurchase(user.id_usuario)
                            }
                        } else {
                            // Manejar el caso de respuesta no exitosa
                            Toast.makeText(this@PaymentSuccessfulActivity, "No existen elementos", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            )
        }
}