package dev.android.appbuses

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
<<<<<<< Updated upstream
=======
import android.content.SharedPreferences
import android.os.Build
>>>>>>> Stashed changes
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.internal.ContextUtils.getActivity
<<<<<<< Updated upstream
=======
import com.google.firebase.auth.FirebaseAuth
import dev.android.appbuses.database.api
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
    private lateinit var binding : ActivityLoginBinding
=======
    private lateinit var binding: ActivityLoginBinding

    @RequiresApi(Build.VERSION_CODES.O)
>>>>>>> Stashed changes
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

<<<<<<< Updated upstream
=======
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun login() {
>>>>>>> Stashed changes
        binding.btnNext.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
            }
            startActivity(intent)
        }
    }
<<<<<<< Updated upstream
=======

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
    private fun session() {
        val preferences: SharedPreferences =
            getSharedPreferences("PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
        val email: String? = preferences.getString("email", null)
        if (email != null) {
            showNewActivity(email)
        }
    }

>>>>>>> Stashed changes
}