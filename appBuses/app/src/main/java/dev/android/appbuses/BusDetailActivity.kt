package dev.android.appbuses

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.Window
import android.widget.TextView
import com.squareup.picasso.Picasso
import dev.android.appbuses.databinding.ActivityBusDetailBinding

class BusDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityBusDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusDetailBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        val window = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(window)
        val width = window.widthPixels
        val height = window.heightPixels

        getWindow().setLayout(((width * 1)), 1400)
        getWindow().decorView.setBackgroundResource(android.R.color.transparent)
        getWindow().setGravity(Gravity.BOTTOM)

        val bundle = intent.extras
        val option = bundle?.getString("option")

        binding.btnBuy.setOnClickListener {
            var intent: Intent
            if (option == "login") {
                intent = Intent(this, PaymentActivity::class.java)
            } else {
                intent = Intent(this, LoginActivity::class.java)
            }
            startActivity(intent)
        }
    }
}