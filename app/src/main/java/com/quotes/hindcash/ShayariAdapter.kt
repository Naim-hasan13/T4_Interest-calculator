package com.quotes.hindcash

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.quotes.hindcash.databinding.ItemShayariBinding

class ShayariAdapter(
    private val shayariList: List<Shayari>
) : RecyclerView.Adapter<ShayariAdapter.ShayariViewHolder>() {

    inner class ShayariViewHolder(val binding: ItemShayariBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShayariViewHolder {
        val binding = ItemShayariBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShayariViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShayariViewHolder, position: Int) {
        val shayari = shayariList[position]
        holder.binding.tvShayari.text = shayari.text
    }

    override fun getItemCount() = shayariList.size
}
