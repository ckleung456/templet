package com.ck.myapplication

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class AdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val image: ImageView = itemView.findViewById(R.id.large_image)

    fun onBinding(hit: Hit) {
        if (hit.largeImageURL.isNotEmpty()) {
            Picasso.get().load(hit.largeImageURL).into(image)
        }
    }
}