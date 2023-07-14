package dev.android.appbuses

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import dev.android.appbuses.database.api
import dev.android.appbuses.databinding.ActivityChangePasswordBinding
import dev.android.appbuses.models.Contrasenia
import dev.android.appbuses.models.Usuario
import kotlinx.android.synthetic.main.activity_change_password.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChangePasswordBinding
    private lateinit var bundle: Bundle
    private lateinit var user: Usuario
    private lateinit var auth: FirebaseAuth
    private var password: String = ""
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        bundle = intent.extras!!
        val email = bundle.getString("email")

        if (email != null) {
            getUser(email)
            getPassword(email)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnSave.setOnClickListener {
            val newPassword = edtPassword.text.toString()
            val currentUser = auth.currentUser

//            Toast.makeText(this@ChangePasswordActivity, password, Toast.LENGTH_SHORT).show()
            if (edtPassword.text.toString().length > 7) {
                if (edtPassword.text.toString() == edtPasswordAgain.text.toString()) {
                    if (edtCurrentPassword.text.toString().isNotEmpty()){
                        if (password == edtCurrentPassword.text.toString()) {
                            val credential = EmailAuthProvider.getCredential(currentUser?.email ?: "", edtCurrentPassword.text.toString())

                            // Reautenticar al usuario con su contraseña actual
                            currentUser?.reauthenticate(credential)?.addOnCompleteListener { reauthTask ->
                                if (reauthTask.isSuccessful) {
                                    // La reautenticación fue exitosa, se puede proceder a cambiar la contraseña
                                    currentUser.updatePassword(newPassword)?.addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Log.d(TAG, "User password updated successfully.")
                                            // Cambiar la contraseña en Firebase Authentication exitosamente.
                                            // Aquí puedes realizar otras tareas relacionadas con el cambio de contraseña.

                                            // Cerrar la sesión actual y volver a iniciar sesión con la nueva contraseña
                                            changePassword(user, edtPassword.text.toString())
                                            finish()
//                                            auth.signOut()
//                                            cerrarSesion()
                                        } else {
                                            Log.d(TAG, "Failed to update user password.", task.exception)
                                            Toast.makeText(this, "No se pudo actualizar la contraseña.", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                } else {
                                    Log.d(TAG, "Reauthentication failed.", reauthTask.exception)
                                    Toast.makeText(this, "Error al volver a autenticar al usuario.", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            Toast.makeText(this@ChangePasswordActivity, "Contraseña actual incorrecta", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@ChangePasswordActivity, "Contraseña actual incorrecta", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@ChangePasswordActivity, "Contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@ChangePasswordActivity, "Mínimo 8 caracteres", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun changePassword(user: Usuario, password: String) {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://nilotic-quart.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api::class.java)
        val retrofit = retrofitBuilder.updateUserPassword(user.id_usuario, password)
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
                        }
                    } else {
                        // Manejar el caso de respuesta no exitosa
                        Toast.makeText(this@ChangePasswordActivity, "No existen elementos", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getPassword(email_usuario: String){
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://nilotic-quart.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api::class.java)
        val retrofit = retrofitBuilder.getPassword(email_usuario)
        retrofit.enqueue(
            object : Callback<Contrasenia> {
                override fun onFailure(call: Call<Contrasenia>, t: Throwable) {
                    Log.d("Agregar", t.message.toString())
                }
                override fun onResponse(call: Call<Contrasenia>, response: Response<Contrasenia> ) {
                    if (response.isSuccessful) {
                        val contrasenia = response.body()
                        Log.d("Respuesta", contrasenia.toString())
                        if (contrasenia != null) {
                            password = contrasenia.clave_usuario
                        }
                    } else {
                        // Manejar el caso de respuesta no exitosa
                        Toast.makeText(this@ChangePasswordActivity, "No existen elementos", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        )
    }

    fun cerrarSesion() {
        val preferencias: SharedPreferences.Editor =
            getSharedPreferences("PREFERENCE_FILE_KEY", Context.MODE_PRIVATE).edit()
        preferencias.clear()
        preferencias.apply()
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }
}