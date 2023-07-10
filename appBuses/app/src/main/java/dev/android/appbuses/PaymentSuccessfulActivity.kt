package dev.android.appbuses

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import dev.android.appbuses.databinding.ActivityPaymentSuccessfulBinding

class PaymentSuccessfulActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPaymentSuccessfulBinding
<<<<<<< Updated upstream
=======
    private lateinit var bundle: Bundle
    private lateinit var user: Usuario
//    private lateinit var purchase: Compra

    @RequiresApi(Build.VERSION_CODES.O)
>>>>>>> Stashed changes
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentSuccessfulBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        binding.btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java).apply {
            }
            startActivity(intent)
        }

        binding.btnBuy.setOnClickListener {
            val intent = Intent(this, QRCodeActivity::class.java).apply {
            }
            startActivity(intent)
        }
    }
<<<<<<< Updated upstream
=======

//    private fun addSaleDatail(idNumber: String, id: Int) {
//        val retrofitBuilder = Retrofit.Builder()
//            .baseUrl("https://nilotic-quart.000webhostapp.com/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(api::class.java)
//        val retrofit = retrofitBuilder.insertDataDetail(id, 1, 0f, 0f, idNumber)
//        retrofit.enqueue(
//            object : Callback<Compra_Detalle> {
//                @RequiresApi(Build.VERSION_CODES.O)
//                override fun onFailure(call: Call<Compra_Detalle>, t: Throwable) {
//                    Log.d("Agregar", "Error al agregar cliente")
//                }
//                override fun onResponse(call: Call<Compra_Detalle>, response: retrofit2.Response<Compra_Detalle>) {
//                    Log.d("Añadido", "Cliente agregado con éxito")
//
//                }
//            }
//        )
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    fun getPurchase(id_comprador: Int){
//        val retrofitBuilder = Retrofit.Builder()
//            .baseUrl("https://nilotic-quart.000webhostapp.com/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(api::class.java)
//        val retrofit = retrofitBuilder.getLastPurchase(id_comprador)
//        retrofit.enqueue(
//            object : Callback<Compra> {
//                override fun onFailure(call: Call<Compra>, t: Throwable) {
//
//                    Log.d("Agregar", t.message.toString())
//                }
//                override fun onResponse(call: Call<Compra>, response: retrofit2.Response<Compra> ) {
//                    if (response.isSuccessful) {
//                        val compra = response.body()
//                        if (compra != null) {
//                            purchase = compra
//                             Toast.makeText(applicationContext, purchase.id_venta.toString(), Toast.LENGTH_SHORT).show()
//
//                                val listaExtra = bundle?.getStringArrayList("listaExtra")
//                                val passAmount = bundle.getInt("cantidad")
//                                Log.d("Size", listaExtra.toString())
//                                for (i in 0 until passAmount){
//                                    listaExtra?.get(i)?.let { addSaleDatail(it, purchase.id_venta) }
//                            }
//                        }
//                    } else {
//                        // Manejar el caso de respuesta no exitosa
//                        Toast.makeText(this@PaymentSuccessfulActivity, "No existen elementos", Toast.LENGTH_SHORT).show()
//                    }
//
//                }
//            }
//        )
//    }

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
//                                getPurchase(user.id_usuario)
                            }
                        } else {
                            // Manejar el caso de respuesta no exitosa
                            Toast.makeText(this@PaymentSuccessfulActivity, "No existen elementos", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            )
        }
>>>>>>> Stashed changes
}