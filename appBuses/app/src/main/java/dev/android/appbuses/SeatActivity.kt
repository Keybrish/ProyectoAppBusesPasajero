package dev.android.appbuses

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import dev.android.appbuses.databinding.ActivitySeatBinding
import dev.android.appbuses.utils.Constants
import java.nio.charset.StandardCharsets

class SeatActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySeatBinding
    private val adapter: SeatsAdapter by lazy{
        SeatsAdapter()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeatBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java).apply {
            }
            startActivity(intent)
        }

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, FileActivity::class.java).apply {
            }
            startActivity(intent)
        }

        val bundle = intent.extras
        val option = bundle?.getString("amount")
        if (option != null) {
            cargarDatos(option.toInt())
        }
    }

    fun cargarDatos(amount: Int) {
        binding.rvFrequency.adapter = adapter
        binding.rvFrequency.layoutManager = LinearLayoutManager(this)
        binding.rvFrequency.setHasFixedSize(true)
        val seats = mutableListOf<Int>()
        for (i in 1..amount)
            seats.add(i)
        adapter.seats = seats
    }
}