package dev.android.appbuses

import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import dev.android.appbuses.databinding.ActivityProfileBinding


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnLogout.setOnClickListener {
            val intent: Intent = Intent(this, WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        binding.btnEditInfo.setOnClickListener {
            val intent = Intent(this, ProfileInfoActivity::class.java).apply {
            }
            startActivity(intent)
        }

        binding.btnChangePassword.setOnClickListener {
            val intent = Intent(this, ChangePasswordActivity::class.java).apply {
            }
            startActivity(intent)
        }

        binding.btnHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java).apply {
            }
            startActivity(intent)
        }

        val loadImage = registerForActivityResult(ActivityResultContracts.GetContent(), ActivityResultCallback {
            binding.imgProfile.setImageURI(it)
        })

        binding.btnCamera.setOnClickListener {
            loadImage.launch("image/*")
        }
    }
}