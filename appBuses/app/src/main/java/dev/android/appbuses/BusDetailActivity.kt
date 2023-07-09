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
import dev.android.appbuses.models.Frecuencia
import dev.android.appbuses.utils.Constants

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

        getWindow().setLayout(((width * 1)), 1400)
        getWindow().decorView.setBackgroundResource(android.R.color.transparent)
        getWindow().setGravity(Gravity.BOTTOM)

        val bundle = intent.extras
        val option = bundle?.getString("option")

        binding.btnBuy.setOnClickListener {
            var intent: Intent
            if (option == "login") {
                intent = Intent(this, PaymentActivity::class.java).apply {
                    putExtras(bundle)
                }
            } else {
                intent = Intent(this, LoginActivity::class.java)
            }
            startActivity(intent)
        }

        bundle?.let {
            val frequency = it.getSerializable(Constants.KEY_FREQUENCY) as Frecuencia

            binding.txtCooperative.text = frequency.nombre_cooperativa
            Picasso.get().load(frequency.fotografia).error(R.drawable.terminal_terrestre_quito).into(binding.imgBus)
            binding.txtRoute.text = frequency.destino + ", " + frequency.destinoProvincia
            binding.txtDate.text = frequency.fecha_viaje
            binding.txtDepartureTime.text = frequency.hora_salida_viaje.substring(0,5)
            binding.txtArrivalTime.text = frequency.hora_llegada_viaje.substring(0,5)
            binding.txtBusNumber.text = frequency.numero_bus
            binding.txtBusCarriage.text = frequency.placa_bus
            binding.txtBusChassis.text = frequency.chasis_bus
            binding.txtBusBodywork.text = frequency.carroceria_bus
            binding.txtPrice.text = "$" + String.format("%.2f", frequency.costo_parada)
        }
    }
}