package dev.android.appbuses

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.ArrayAdapter
import dev.android.appbuses.databinding.ActivityPaymentBinding

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
            if(binding.txtAmount.text.toString().toInt() > 0)
                binding.txtAmount.text = (binding.txtAmount.text.toString().toInt() - 1).toString()
        }

        binding.btnPlus.setOnClickListener {
            binding.txtAmount.text = (binding.txtAmount.text.toString().toInt() + 1).toString()
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}