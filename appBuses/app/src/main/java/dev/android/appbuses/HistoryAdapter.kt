package dev.android.appbuses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.android.appbuses.databinding.ItemFrequencyBinding
import dev.android.appbuses.databinding.ItemHistoryBinding
import dev.android.appbuses.models.Compra
import dev.android.appbuses.models.Frecuencia

class HistoryAdapter(var compras: List<Compra> = emptyList()) : RecyclerView.Adapter<HistoryAdapter.HistoryAdapterViewHolder>() {
    //crear el viewHolder
    lateinit var setOnClickListener:(Compra) -> Unit
    inner class HistoryAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding: ItemHistoryBinding = ItemHistoryBinding.bind(itemView)

        fun bind(compras: Compra) {
            binding.txtRoute.text = compras.origen + " - " + compras.destino
            binding.txtDate.text = compras.fecha_viaje
            binding.txtTime.text = compras.hora_salida_viaje.substring(0,5) + " - " + compras.hora_llegada_viaje.substring(0,5)
            binding.root.setOnClickListener {
                setOnClickListener(compras)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryAdapterViewHolder, position: Int) {
        val compras = compras[position]
        holder.bind(compras)
    }

    override fun getItemCount(): Int {
        return compras.size
    }
    fun updateListFrequencies(compras:List<Compra>){
        this.compras=compras
        notifyDataSetChanged()
    }
}