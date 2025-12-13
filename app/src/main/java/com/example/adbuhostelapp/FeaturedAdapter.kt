package com.example.adbuhostelapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FeaturedAdapter(private val items: List<FeaturedItem>) :
    RecyclerView.Adapter<FeaturedAdapter.FeaturedViewHolder>() {

    class FeaturedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.featured_image)
        val title: TextView = itemView.findViewById(R.id.featured_title)
        val rating: RatingBar = itemView.findViewById(R.id.featured_rating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.featured_card_design, parent, false)
        return FeaturedViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeaturedViewHolder, position: Int) {
        val item = items[position]
        holder.image.setImageResource(item.imageRes)
        holder.title.text = item.title
        holder.rating.rating = item.rating
    }

    override fun getItemCount(): Int = items.size
}
