package dev.android.appbuses

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import dev.android.appbuses.databinding.ActivityLoginBinding
import dev.android.appbuses.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
            }
            startActivity(intent)
        }
    }
<<<<<<< Updated upstream
=======
    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registro exitoso
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, WelcomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Error en el registro
                    Toast.makeText(this, "Error en el registro: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
>>>>>>> Stashed changes
}