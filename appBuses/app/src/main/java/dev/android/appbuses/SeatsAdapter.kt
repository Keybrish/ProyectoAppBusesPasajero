package dev.android.appbuses

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import dev.android.appbuses.databinding.ItemSeatBinding
import dev.android.appbuses.models.Frecuencia
import kotlinx.android.synthetic.main.item_seat.view.*
import org.json.JSONArray
import org.json.JSONException

class SeatsAdapter(var seats: List<Int> = emptyList()) : RecyclerView.Adapter<SeatsAdapter.SeatsAdapterViewHolder>() {
    //crear el viewHolder
    var seatType = mutableListOf<String>()
    inner class SeatsAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding: ItemSeatBinding = ItemSeatBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bind() {
            binding.txtSeatNumber.text = "Seat " + seats[position]
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeatsAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_seat, parent, false)

        view.spnType.dropDownVerticalOffset
//        val op = listOf("VIP", "Ejecutivo", "Estándar")
        val op = seatType
        val sp = ArrayAdapter(parent.context, android.R.layout.simple_spinner_item, op)
        view.spnType.adapter = sp
        return SeatsAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: SeatsAdapterViewHolder, position: Int) {
        val seats = seats[position]
        return holder.bind()
    }

    override fun getItemCount(): Int {
        return seats.size
    }
    fun updateListFrequencies(seats:List<Int>){
        this.seats = seats
        notifyDataSetChanged()
    }

}