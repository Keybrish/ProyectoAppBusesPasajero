package dev.android.appbuses

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
import com.google.firebase.auth.FirebaseAuth
import dev.android.appbuses.database.api
import dev.android.appbuses.databinding.ActivityRegisterBinding
import dev.android.appbuses.models.Usuario
import dev.android.appbuses.models.Usuario_Registro
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var user: Usuario
    private lateinit var usser: Usuario_Registro
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnNext.setOnClickListener {
            email = binding.edtEmail.text.toString()
            password = binding.edtPassword.text.toString()
            val name = binding.edtName.text.toString()
            val lastName = binding.edtLastName.text.toString()
            usser = Usuario_Registro("", email, password, name, lastName, "")

            if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty() && lastName.isNotEmpty()) {
                registerUser(email, password)
            } else {
                Toast.makeText(this, "Llene todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registro exitoso
                    createUser(usser)
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    val intent =
                        Intent(this, MainActivity::class.java).apply {
                            putExtra("email", email)
                            val preferences: SharedPreferences.Editor =
                                getSharedPreferences(
                                    "PREFERENCE_FILE_KEY",
                                    Context.MODE_PRIVATE
                                ).edit()
                            preferences.putString("email", email)
                            preferences.apply()
                        }
                    startActivity(intent)
                } else {
                    // Error en el registro
                    Toast.makeText(this, "Error en el registro: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun createUser(usuarioRegistro: Usuario_Registro) {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://nilotic-quart.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api::class.java)
        val retrofit = retrofitBuilder.createUser(usuarioRegistro.cedula_usuario, usuarioRegistro.email_usuario, usuarioRegistro.clave_usuario, usuarioRegistro.nombre_usuario, usuarioRegistro.apellido_usuario, usuarioRegistro.telefono_usuario)
        retrofit.enqueue(
            object : Callback<Usuario_Registro> {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onFailure(call: Call<Usuario_Registro>, t: Throwable) {
                    Log.d("Agregar", "Error al agregar cliente")
                }
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(call: Call<Usuario_Registro>, response: retrofit2.Response<Usuario_Registro>) {
                    Log.d("Usuario", "Registrado")
                    getUser(usuarioRegistro.email_usuario)
                }
            }
        )
    }

    private fun updateUser(user: Usuario) {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://nilotic-quart.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api::class.java)
        val retrofit = retrofitBuilder.updateUser(user.id_usuario, user.email_usuario, user.nombre_usuario, user.apellido_usuario, user.telefono_usuario, user.foto_usuario)
        retrofit.enqueue(
            object : Callback<Usuario> {
                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    Log.d("Agregar", t.message.toString())
                }
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                    Log.d("Agregar", response.message().toString())
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
                            user.foto_usuario = "https://i.pinimg.com/originals/22/ea/2f/22ea2f12077c815385bdca7ac34d9d59.jpg"
                            updateUser(user)
                        }
                    } else {
                        // Manejar el caso de respuesta no exitosa
                        Toast.makeText(this@RegisterActivity, "No existen elementos", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
    }
}