package com.hestia.Interface

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hestia.R

class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var imageView: ImageView = itemView.findViewById(R.id.ivImage)
    var mTitle: TextView = itemView.findViewById(R.id.tvTitle)
    var mDescription: TextView = itemView.findViewById(R.id.tvDescription)
    var mDifficulty: TextView = itemView.findViewById(R.id.tvDifficulty)

}