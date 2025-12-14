package com.example.adbuhostelapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.adbuhostelapp.R
import com.example.adbuhostelapp.model.Complaint

class ComplaintAdapter(private val list: List<Complaint>) :
    RecyclerView.Adapter<ComplaintAdapter.VH>() {

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val desc: TextView = itemView.findViewById(R.id.tvDesc)
        val status: TextView = itemView.findViewById(R.id.tvStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_complaint, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val c = list[position]
        holder.title.text = c.title
        holder.desc.text = c.description
        holder.status.text = "Status: ${c.status}"
    }

    override fun getItemCount(): Int = list.size
}
