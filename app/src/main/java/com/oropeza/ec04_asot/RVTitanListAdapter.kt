package com.oropeza.ec04_asot

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oropeza.ec04_asot.databinding.ItemTitanBinding
import com.oropeza.ec04_asot.model.AttackOnTitan

class RVTitanListAdapter(var titans: List<AttackOnTitan>, val onClick: (AttackOnTitan) -> Unit): RecyclerView.Adapter<TitanVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitanVH {
        val binding = ItemTitanBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TitanVH(binding, onClick)
    }

    override fun getItemCount(): Int = titans.size

    override fun onBindViewHolder(holder: TitanVH, position: Int) {
        holder.bind(titans[position])
    }
}

class TitanVH(private val binding: ItemTitanBinding, val onClick: (AttackOnTitan) -> Unit): RecyclerView.ViewHolder(binding.root) {
    fun bind(titan: AttackOnTitan) {
        Glide.with(binding.root)
            .load(titan.img)
            .into(binding.imageView)
        binding.txtName.text = titan.name
        binding.root.setOnClickListener {
            onClick(titan)
        }
    }
}