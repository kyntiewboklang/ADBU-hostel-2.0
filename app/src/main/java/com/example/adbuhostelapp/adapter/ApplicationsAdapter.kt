package com.example.adbuhostelapp.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.adbuhostelapp.ui.AllotRoomActivity
import com.example.adbuhostelapp.R
import com.example.adbuhostelapp.model.RoomApplication

class ApplicationsAdapter(
    private val list: List<Pair<String, RoomApplication>> // docId + data
) : RecyclerView.Adapter<ApplicationsAdapter.VH>() {

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val subtitle: TextView = itemView.findViewById(R.id.tvSubtitle)
        val btnAllot: Button = itemView.findViewById(R.id.btnAllot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_application, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val (docId, app) = list[position]

        holder.title.text = "${app.fullName} (${app.studentId})"
        holder.subtitle.text = "Room: ${app.roomType} | Status: ${app.status}"

        holder.btnAllot.setOnClickListener {
            val intent = Intent(holder.itemView.context, AllotRoomActivity::class.java)
            intent.putExtra("DOC_ID", docId)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = list.size
}
