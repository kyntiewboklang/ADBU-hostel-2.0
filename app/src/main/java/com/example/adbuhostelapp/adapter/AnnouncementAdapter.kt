package com.example.adbuhostelapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.adbuhostelapp.R
import com.example.adbuhostelapp.model.Announcement
import com.google.firebase.firestore.FirebaseFirestore

class AnnouncementAdapter(
    private val isAdmin: Boolean = false
) : RecyclerView.Adapter<AnnouncementAdapter.VH>() {

    private val list = mutableListOf<Pair<String, Announcement>>()
    private val db = FirebaseFirestore.getInstance()

    fun setData(data: List<Pair<String, Announcement>>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_announcement, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val (docId, a) = list[position]

        holder.title.text = a.title
        holder.message.text = a.message

        if (isAdmin) {
            holder.delete.visibility = View.VISIBLE
            holder.delete.setOnClickListener {
                db.collection("announcements")
                    .document(docId)
                    .delete()
            }
        } else {
            holder.delete.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = list.size

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val title: TextView = v.findViewById(R.id.tv_title)
        val message: TextView = v.findViewById(R.id.tv_message)
        val delete: ImageView = v.findViewById(R.id.btn_delete)
    }
}
