package com.ck.myapplication

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class SquareViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val context = itemView.context
    val imageView = itemView.findViewById<ImageView>(R.id.large_image)

    fun binding(dataModel: DataModel) {
        imageView.setBackgroundColor(ContextCompat.getColor(context, dataModel.colorId))
    }
}