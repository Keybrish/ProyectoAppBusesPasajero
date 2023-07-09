package dev.android.appbuses

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.firebase.auth.FirebaseAuth
import dev.android.appbuses.databinding.ActivityLoginBinding
import dev.android.appbuses.databinding.ActivityWelcomeBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

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

    private fun login() {
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

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Ingrese correctamente su usuario y contraseña")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showNewActivity(email: String) {
        val preferences: SharedPreferences.Editor =
            getSharedPreferences(
                "PREFERENCE_FILE_KEY",
                Context.MODE_PRIVATE
            ).edit()
        preferences.putString("email", email)
        preferences.apply()
        val intent =
            Intent(this, MainActivity::class.java)
        startActivity(intent)

    }
    private fun session() {
        val preferences: SharedPreferences =
            getSharedPreferences("PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
        val email: String? = preferences.getString("email", null)
        if (email != null) {
            showNewActivity(email)
        }
    }


}