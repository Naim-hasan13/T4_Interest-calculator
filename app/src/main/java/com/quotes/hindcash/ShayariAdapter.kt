package com.quotes.hindcash

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ShayariAdapter(private var shayariList: List<Shayari>) : RecyclerView.Adapter<ShayariAdapter.ShayariViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShayariViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shayari, parent, false)
        return ShayariViewHolder(view)
    }

    // Method to update the list
    fun updateShayariList(newShayariList: List<Shayari>) {
        shayariList = newShayariList
        notifyDataSetChanged()  // Notify adapter to refresh the RecyclerView
    }


    override fun onBindViewHolder(holder: ShayariViewHolder, position: Int) {
        val shayari = shayariList[position]
        holder.bind(shayari)
    }

    override fun getItemCount() = shayariList.size

    class ShayariViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(shayari: Shayari) {
            itemView.findViewById<TextView>(R.id.tvShayari).text = shayari.text
        }
    }
}
