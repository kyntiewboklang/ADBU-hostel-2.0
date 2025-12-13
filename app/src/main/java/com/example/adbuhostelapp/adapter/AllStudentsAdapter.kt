package com.example.adbuhostelapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.adbuhostelapp.R
import com.example.adbuhostelapp.model.RoomApplication

class AllStudentsAdapter(
    private val list: List<RoomApplication>
) : RecyclerView.Adapter<AllStudentsAdapter.VH>() {

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvStudentId: TextView = view.findViewById(R.id.tvStudentId)
        val tvPhone: TextView = view.findViewById(R.id.tvPhone)
        val tvDob: TextView = view.findViewById(R.id.tvDob)
        val tvAddress: TextView = view.findViewById(R.id.tvAddress)
        val tvRoomType: TextView = view.findViewById(R.id.tvRoomType)
        val tvAllottedRoom: TextView = view.findViewById(R.id.tvAllottedRoom)
        val tvStatus: TextView = view.findViewById(R.id.tvStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student_full, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val app = list[position]

        holder.tvName.text = "Name: ${app.fullName}"
        holder.tvStudentId.text = "ID: ${app.studentId}"
        holder.tvPhone.text = "Phone: ${app.phone}"
        holder.tvDob.text = "DOB: ${app.dob}"
        holder.tvAddress.text = "Address: ${app.address}"
        holder.tvRoomType.text = "Room Type: ${app.roomType}"

        holder.tvAllottedRoom.text =
            if (app.allottedRoom.isEmpty())
                "Allotted Room: —"
            else
                "Allotted Room: ${app.allottedRoom}"

        holder.tvStatus.text = "Status: ${app.status}"
    }

    override fun getItemCount(): Int = list.size
}
