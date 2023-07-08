package dev.android.appbuses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.android.appbuses.databinding.ItemFrequencyBinding
import dev.android.appbuses.models.Frecuencia

class FrequencyAdapter(var frecuencias: List<Frecuencia> = emptyList()) : RecyclerView.Adapter<FrequencyAdapter.FrequencyAdapterViewHolder>() {
    //crear el viewHolder
    lateinit var setOnClickListener:(Frecuencia) -> Unit
    inner class FrequencyAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding: ItemFrequencyBinding = ItemFrequencyBinding.bind(itemView)

        fun bind(frecuencias: Frecuencia) {
            Picasso.get().load(frecuencias.fotografia).error(R.drawable.terminal_terrestre_quito).into(binding.imgBus)
            binding.txtFrequency.text = frecuencias.nombre_cooperativa + ", " + frecuencias.origen_frecuencia + " - " + frecuencias.destino_frecuencia

            binding.root.setOnClickListener {
                setOnClickListener(frecuencias)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FrequencyAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_frequency, parent, false)
        return FrequencyAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: FrequencyAdapterViewHolder, position: Int) {
        val frecuencias = frecuencias[position]
        holder.bind(frecuencias)
    }

    override fun getItemCount(): Int {
        return frecuencias.size
    }
    fun updateListFrequencies(frecuencias:List<Frecuencia>){
        this.frecuencias=frecuencias
        notifyDataSetChanged()
    }
}