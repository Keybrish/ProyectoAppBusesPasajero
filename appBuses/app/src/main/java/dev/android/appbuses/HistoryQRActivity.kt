package dev.android.appbuses

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import dev.android.appbuses.database.api
import dev.android.appbuses.databinding.ActivityHistoryQractivityBinding
import dev.android.appbuses.models.Compra
import dev.android.appbuses.models.Frecuencia
import dev.android.appbuses.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HistoryQRActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHistoryQractivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryQractivityBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

    }
}