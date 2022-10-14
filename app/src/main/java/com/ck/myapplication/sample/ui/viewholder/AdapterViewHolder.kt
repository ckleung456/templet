package com.ck.myapplication.sample.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.ck.myapplication.base.utils.setOnThrottleClickListener
import com.ck.myapplication.databinding.AdapterBinding
import com.ck.myapplication.sample.model.Hit
import com.squareup.picasso.Picasso

class AdapterViewHolder(
    private val binding: AdapterBinding,
    private val onClick: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    private var hit: Hit? = null

    init {
        itemView.setOnThrottleClickListener {
            hit?.let { i ->
                onClick.invoke(i.largeImageURL)
            }
        }
    }

    fun onBinding(hit: Hit) {
        this.hit = hit
        if (hit.largeImageURL.isNotEmpty()) {
            Picasso.get().load(hit.largeImageURL).into(binding.largeImage)
        }
    }
}