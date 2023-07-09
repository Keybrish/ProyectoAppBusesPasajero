package dev.android.appbuses

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
<<<<<<< HEAD
import android.content.SharedPreferences
=======
<<<<<<< Updated upstream
=======
import android.content.SharedPreferences
import android.os.Build
>>>>>>> Stashed changes
>>>>>>> PD-55-Integracion-Servicios-Compras
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.internal.ContextUtils.getActivity
<<<<<<< HEAD
import com.google.firebase.auth.FirebaseAuth
=======
<<<<<<< Updated upstream
=======
import com.google.firebase.auth.FirebaseAuth
import dev.android.appbuses.database.api
>>>>>>> Stashed changes
>>>>>>> PD-55-Integracion-Servicios-Compras
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
<<<<<<< HEAD
    private lateinit var binding: ActivityLoginBinding

=======
<<<<<<< Updated upstream
    private lateinit var binding : ActivityLoginBinding
=======
    private lateinit var binding: ActivityLoginBinding

    @RequiresApi(Build.VERSION_CODES.O)
>>>>>>> Stashed changes
>>>>>>> PD-55-Integracion-Servicios-Compras
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

<<<<<<< HEAD
    }

    private fun login() {
=======
<<<<<<< Updated upstream
=======
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun login() {
>>>>>>> Stashed changes
>>>>>>> PD-55-Integracion-Servicios-Compras
        binding.btnNext.setOnClickListener {
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
        }
    }
<<<<<<< HEAD
=======
<<<<<<< Updated upstream
=======
>>>>>>> PD-55-Integracion-Servicios-Compras

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Ingrese correctamente su usuario y contraseña")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

<<<<<<< HEAD
=======
    @RequiresApi(Build.VERSION_CODES.O)
>>>>>>> PD-55-Integracion-Servicios-Compras
    private fun showNewActivity(email: String) {
        val preferences: SharedPreferences.Editor =
            getSharedPreferences(
                "PREFERENCE_FILE_KEY",
                Context.MODE_PRIVATE
            ).edit()
        preferences.putString("email", email)
<<<<<<< HEAD
        preferences.apply()
        val intent =
            Intent(this, MainActivity::class.java)
        startActivity(intent)

    }
=======
//        preferences.apply()
//        cargarDatos(email)
//        val bundle = Bundle().apply {
//            putSerializable(Constants.KEY_USER, user)
//        }
        Log.d("Email", email)
        val intent =
            Intent(this, MainActivity::class.java).apply {
                putExtra("email", email)
            }
        startActivity(intent)

    }
    @RequiresApi(Build.VERSION_CODES.O)
>>>>>>> PD-55-Integracion-Servicios-Compras
    private fun session() {
        val preferences: SharedPreferences =
            getSharedPreferences("PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
        val email: String? = preferences.getString("email", null)
        if (email != null) {
            showNewActivity(email)
        }
    }

<<<<<<< HEAD

=======
>>>>>>> Stashed changes
>>>>>>> PD-55-Integracion-Servicios-Compras
}