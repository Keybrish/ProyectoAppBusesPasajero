package dev.android.appbuses

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import dev.android.appbuses.databinding.ActivityPaymentSuccessfulBinding

class PaymentSuccessfulActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPaymentSuccessfulBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentSuccessfulBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        binding.btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java).apply {
            }
            startActivity(intent)
        }

        binding.btnBuy.setOnClickListener {
            val intent = Intent(this, QRCodeActivity::class.java).apply {
            }
            startActivity(intent)
        }
    }
}