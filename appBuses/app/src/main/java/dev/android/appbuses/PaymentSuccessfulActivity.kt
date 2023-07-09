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
import dev.android.appbuses.models.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PaymentSuccessfulActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPaymentSuccessfulBinding
    private lateinit var bundle: Bundle
    private lateinit var user: Usuario
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
            val intent = Intent(this, QRCodeActivity::class.java).apply {

                putExtras(bundle)
            }
            startActivity(intent)
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
                            Toast.makeText(this@PaymentSuccessfulActivity, "No existen elementos", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            )
        }
}