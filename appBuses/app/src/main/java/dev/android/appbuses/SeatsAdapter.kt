package dev.android.appbuses

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import dev.android.appbuses.databinding.ItemSeatBinding
import dev.android.appbuses.models.Asiento
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_seat.view.*

class SeatsAdapter(var seats: List<Int> = emptyList()) : RecyclerView.Adapter<SeatsAdapter.SeatsAdapterViewHolder>() {
    //crear el viewHolder
    var seatType = mutableListOf<Asiento>()
    lateinit var context: Context
    lateinit var getSpinnerOption:(Any) -> Unit
    var selectedOptions = mutableListOf<Int>()
    var passengersID = mutableListOf<String>()
    var passengersSeatType = mutableListOf<String>()

    inner class SeatsAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding: ItemSeatBinding = ItemSeatBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bind() {
            binding.txtSeatNumber.text = "Seat " + seats[position]
            binding.spnType.dropDownVerticalOffset
            var type = mutableListOf<String>()
            for (i in 0 until seatType.size){
                type.add(seatType[i].descripcion_asiento)
            }
            val sp = ArrayAdapter(context, android.R.layout.simple_spinner_item, type)
            binding.spnType.adapter = sp

            binding.edtID.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    passengersID[adapterPosition] = binding.edtID.text.toString()
                }

                override fun afterTextChanged(s: Editable?) {}
            })
            binding.spnType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    selectedOptions[adapterPosition] = position // Almacenar el valor seleccionado en la posición correspondiente
                    passengersSeatType[adapterPosition] = binding.spnType.selectedItem.toString()
                    getSpinnerOption(getAllSelectedOptions())
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Manejar el caso en que no se haya seleccionado nada
                    passengersSeatType[adapterPosition] = seatType[0].toString()
                }
            }

        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeatsAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_seat, parent, false)
        for (i in seats.indices){
            selectedOptions.add(0)
            passengersID.add("")
            passengersSeatType.add("")
        }
        return SeatsAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: SeatsAdapterViewHolder, position: Int) {
        val seats = seats[position]
        return holder.bind()
    }

    override fun getItemCount(): Int {
        return seats.size
    }

    fun updateListFrequencies(seats: List<Int>){
        this.seats = seats
        if (seats != null) {
        }
        notifyDataSetChanged()
    }

    fun getAllSelectedOptions(): List<Int> {
//        Toast.makeText(context, selectedOptions.toString(), Toast.LENGTH_SHORT).show()
        return selectedOptions
    }

    fun getAllPassengers(): List<String> {
        return passengersID
    }

    fun getAllPassengersSeats(): List<String> {
        return passengersSeatType
    }
}