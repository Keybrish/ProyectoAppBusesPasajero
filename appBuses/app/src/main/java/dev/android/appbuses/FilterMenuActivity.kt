package dev.android.appbuses

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import dev.android.appbuses.databinding.ActivityFilterMenuBinding
import dev.android.appbuses.databinding.ActivityMainBinding

class FilterMenuActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFilterMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(window)
        val width = window.widthPixels
        val height = window.heightPixels

        getWindow().setLayout(((width * 0.85).toInt()), ((height * 0.36).toInt()))
        getWindow().decorView.setBackgroundResource(android.R.color.transparent)

        binding.txtCooperative.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("filtro", "cooperativa")
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        binding.txtSeat.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("filtro", "asiento")
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        binding.txtChassis.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("filtro", "chasis")
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        binding.txtBodywork.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("filtro", "carroceria")
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        binding.txtRight.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("filtro", "directo")
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        binding.txtStop.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("filtro", "paradas")
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}