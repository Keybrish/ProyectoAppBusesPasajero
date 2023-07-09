package dev.android.appbuses

import android.R
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.paypal.android.sdk.payments.PayPalConfiguration
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity.EXTRA_PAYMENT
import com.squareup.picasso.Picasso
import dev.android.appbuses.databinding.ActivityPaymentBinding
import dev.android.appbuses.models.Frecuencia
import dev.android.appbuses.utils.Constants
import java.math.BigDecimal
import com.paypal.android.sdk.payments.PaymentActivity
import com.paypal.android.sdk.payments.PaymentConfirmation

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPaymentBinding

    //para PAYPAL
    private lateinit var monto :TextView
    private lateinit var botonpago :Button
    private var clienteId :String = "ASPCGNASkHrOhgxreJB8Ok0d-8e6FILUG1CFDfqeEf6bp67Uyk_MLt7RcbLXDCba5KilKzGl8exigsuF" //con el de eliana
    private var PAYPAL_REQUEST_CODE:Int = 1

    private lateinit var configuration : PayPalConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        //para PAYPAL
        monto = binding.textView36
        botonpago = binding.btnNext
        configuration = PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(this.clienteId)

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
            val intent = Intent(this, SeatActivity::class.java).apply {
                if (bundle != null) {
                    putExtras(bundle)
                }
                //putExtra("amount", amount)
            }
            startActivity(intent)
            //getPayment()
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

    fun getPayment(){
        //val amount = binding.txtAmount.text.toString()
        var payment = PayPalPayment(BigDecimal("10.00"), "USD", "Payment Description", PayPalPayment.PAYMENT_INTENT_SALE)
        var intent = Intent(this, PaymentActivity::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration)
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment)

        startActivityForResult(intent, PAYPAL_REQUEST_CODE)
    }

    override fun onDestroy() {
        stopService(Intent(this, PayPalService::class.java))
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val confirm: PaymentConfirmation? = data?.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION)
                if (confirm != null) {

                    try {
                        val paymentDetails = confirm.toJSONObject().toString()
                    }catch (e:Exception){

                    }


                    // Procesar los detalles del pago
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // El usuario canceló el pago
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                // Datos inválidos proporcionados al realizar el pago
            }
        }
    }
}