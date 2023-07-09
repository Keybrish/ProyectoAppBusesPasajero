package dev.android.appbuses

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import com.google.firebase.auth.FirebaseAuth
import dev.android.appbuses.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
        checkSession()
        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java).apply {
            }
            startActivity(intent)
        }

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java).apply {
            }
            startActivity(intent)
        }

        binding.btnSkip.setOnClickListener {
            val intent = Intent(this, MainSkipActivity::class.java)
            startActivity(intent)
        }
    }
<<<<<<< HEAD
=======
<<<<<<< Updated upstream
=======
>>>>>>> PD-55-Integracion-Servicios-Compras

    fun checkSession() {
        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        if (currentUser != null) {
<<<<<<< HEAD
            val intent = Intent(this, MainActivity::class.java)
=======
            currentUser.email?.let { Log.d("Email", it) }
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("email", currentUser.email.toString())
            }
>>>>>>> PD-55-Integracion-Servicios-Compras
            startActivity(intent)
            finish()
            return
        }
    }

<<<<<<< HEAD
=======
>>>>>>> Stashed changes
>>>>>>> PD-55-Integracion-Servicios-Compras
}