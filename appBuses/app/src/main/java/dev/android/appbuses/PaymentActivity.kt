package dev.android.appbuses

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Toast
import com.squareup.picasso.Picasso
import dev.android.appbuses.databinding.ActivityPaymentBinding
import dev.android.appbuses.models.Frecuencia
import dev.android.appbuses.utils.Constants

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        binding.spnPayment.dropDownVerticalOffset
        val op = listOf("Paypal", "Deposito", "Transferencia")
        val sp = ArrayAdapter(this, R.layout.simple_spinner_item, op)
        binding.spnPayment.adapter = sp

        binding.btnLess.setOnClickListener {
            if(binding.txtAmount.text.toString().toInt() > 1)
                binding.txtAmount.text = (binding.txtAmount.text.toString().toInt() - 1).toString()
        }

        binding.btnPlus.setOnClickListener {
            binding.txtAmount.text = (binding.txtAmount.text.toString().toInt() + 1).toString()
        }

        binding.btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java).apply {
            }
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        val bundle = intent.extras

        binding.btnNext.setOnClickListener {
            val amount = binding.txtAmount.text.toString()
            val intent = Intent(this, SeatActivity::class.java).apply {
                if (bundle != null) {
                    putExtras(bundle)
                }
                putExtra("amount", amount)
            }
            startActivity(intent)
        }

        bundle?.let {
            val frequency = it.getSerializable(Constants.KEY_FREQUENCY) as Frecuencia

            binding.txtCooperative.text = frequency.nombre_cooperativa
            binding.txtOrigin.text = frequency.origen_frecuencia
            binding.txtDestination.text = frequency.destino_frecuencia
            binding.txtDate.text = frequency.fecha_viaje
            binding.txtTime.text = frequency.hora_salida.substring(0,5) + " - " + frequency.hora_llegada.substring(0,5)
        }
    }
}