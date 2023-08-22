package com.oropeza.ec04_asot

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oropeza.ec04_asot.databinding.ItemDbTitanBinding
import com.oropeza.ec04_asot.databinding.ItemTitanBinding
import com.oropeza.ec04_asot.model.TitanFirebase

class RVTitanDBListAdapter(var titansDB: List<TitanFirebase>) : RecyclerView.Adapter<TitanDBRV>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitanDBRV {
        val binding = ItemDbTitanBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TitanDBRV(binding)
    }

    override fun getItemCount(): Int = titansDB.size
    fun setData(newData: MutableList<TitanFirebase>){
        titansDB = newData
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TitanDBRV, position: Int) {
        holder.bind(titansDB[position])
    }
}

class TitanDBRV(private val binding: ItemDbTitanBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(titanFirebase: TitanFirebase) {
        Glide.with(binding.root)
            .load(titanFirebase.img)
            .into(binding.imageDb)
        binding.txtNombre.text = titanFirebase.name
        binding.txtGenero.text = titanFirebase.gender
        binding.txtStatus.text = titanFirebase.status
        binding.txtOcupacion.text = titanFirebase.occupation
        binding.txtLugNaci.text = titanFirebase.birthplace
    }
}