package dev.android.appbuses

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import dev.android.appbuses.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

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
<<<<<<< Updated upstream
=======

    fun checkSession() {
        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            currentUser.email?.let { Log.d("Email", it) }
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("email", currentUser.email.toString())
            }
            startActivity(intent)
            finish()
            return
        }
    }

>>>>>>> Stashed changes
}