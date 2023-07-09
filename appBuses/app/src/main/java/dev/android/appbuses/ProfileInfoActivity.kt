package dev.android.appbuses

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.annotation.RequiresApi
import dev.android.appbuses.database.api
import dev.android.appbuses.databinding.ActivityProfileInfoBinding
import dev.android.appbuses.models.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileInfoActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProfileInfoBinding
    private lateinit var user: Usuario
    private lateinit var bundle: Bundle
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileInfoBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnSave.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java).apply {
            }
            startActivity(intent)
        }

        bundle = intent.extras!!
        val email = bundle?.getString("email")
        Toast.makeText(this@ProfileInfoActivity, email.toString(), Toast.LENGTH_SHORT).show()
        if (email != null) {
            getUser(email)
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
                            binding.edtName.setText(user.nombre_usuario)
                            binding.edtLastName.setText(user.apellido_usuario)
                            binding.edtID.setText(user.cedula_usuario)
                            binding.edtPhone.setText(user.telefono_usuario)
                            binding.edtEmail.setText(user.email_usuario)
                            binding.edtEmail.isEnabled = false;
                        }
                    } else {
                        // Manejar el caso de respuesta no exitosa
                        Toast.makeText(this@ProfileInfoActivity, "No existen elementos", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        )
    }
}