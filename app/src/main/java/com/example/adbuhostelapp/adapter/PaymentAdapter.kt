package com.example.adbuhostelapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.adbuhostelapp.R
import com.example.adbuhostelapp.model.Payment
import java.text.SimpleDateFormat
import java.util.*

class PaymentAdapter(
    private val list: MutableList<Payment>
) : RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder>() {

    class PaymentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvRoom: TextView = view.findViewById(R.id.tvRoom)
        val tvAmount: TextView = view.findViewById(R.id.tvAmount)
        val tvMethod: TextView = view.findViewById(R.id.tvMethod)
        val tvTime: TextView = view.findViewById(R.id.tvTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_payment, parent, false)
        return PaymentViewHolder(view)
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        val p = list[position]

        holder.tvName.text = "Name: ${p.name}"
        holder.tvRoom.text = "Room: ${p.room}"
        holder.tvAmount.text = "Amount: ₹${p.amount}"
        holder.tvMethod.text = "Method: ${p.method}"

        val date = p.timestamp?.toDate()
        holder.tvTime.text = if (date != null) {
            "Date: " + SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(date)
        } else {
            "Date: -"
        }
    }

    override fun getItemCount(): Int = list.size
}
