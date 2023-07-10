package dev.android.appbuses

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import dev.android.appbuses.database.api
import dev.android.appbuses.databinding.ActivityLoginBinding
import dev.android.appbuses.databinding.ActivityWelcomeBinding
import dev.android.appbuses.models.Frecuencia
import dev.android.appbuses.models.Usuario
import dev.android.appbuses.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var user: Usuario

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
        login()
        session()
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

        @RequiresApi(Build.VERSION_CODES.O)
        private fun login() {
            binding.btnNext.setOnClickListener {
                if (binding.editTextTextPassword.text.isNotEmpty() && binding.editTextTextPassword1.text.isNotEmpty()) {
                    getUser(binding.editTextTextPassword.text.toString())
                } else {
                    Toast.makeText(this@LoginActivity, "Llene todos los campos", Toast.LENGTH_SHORT).show()
                }
            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sessionSuccessful (tipo_usuario: String) {
        if(tipo_usuario == "pasajero") {
            if (binding.editTextTextPassword.text.isNotEmpty() && binding.editTextTextPassword1.text.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    binding.editTextTextPassword.text.toString(),
                    binding.editTextTextPassword1.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val email = binding.editTextTextPassword.text.toString()
                        showNewActivity(email ?: "")
                    } else {
                        showAlert()
                    }
                }
            }
        } else {
            Toast.makeText(this@LoginActivity, "Usuario no registrado", Toast.LENGTH_SHORT).show()
        }
    }

        private fun showAlert() {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Error")
            builder.setMessage("Ingrese correctamente su usuario y contraseña")
            builder.setPositiveButton("Aceptar", null)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        @RequiresApi(Build.VERSION_CODES.O)
        private fun showNewActivity(email: String) {
            val preferences: SharedPreferences.Editor =
                getSharedPreferences(
                    "PREFERENCE_FILE_KEY",
                    Context.MODE_PRIVATE
                ).edit()
            preferences.putString("email", email)
            preferences.apply()
            Log.d("Email", email)
            val intent =
                Intent(this, MainActivity::class.java).apply {
                    putExtra("email", email)
                }
            startActivity(intent)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        private fun session() {
            val preferences: SharedPreferences =
                getSharedPreferences("PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
            val email: String? = preferences.getString("email", null)
            if (email != null) {
                showNewActivity(email)
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
                            if (user.email_usuario != null) {
                                sessionSuccessful(usuario.tipo_usuario)
                            } else {
                                showAlert()
                            }
                        }
                    } else {
                        // Manejar el caso de respuesta no exitosa
                        Toast.makeText(this@LoginActivity, "No existen elementos", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
    }
}