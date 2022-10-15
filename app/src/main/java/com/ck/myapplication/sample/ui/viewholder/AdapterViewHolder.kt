package com.ck.myapplication.sample.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.ck.myapplication.base.utils.setGone
import com.ck.myapplication.base.utils.setOnThrottleClickListener
import com.ck.myapplication.base.utils.setVisible
import com.ck.myapplication.databinding.AdapterBinding
import com.ck.myapplication.sample.model.Hit
import com.squareup.picasso.Picasso

class AdapterViewHolder(
    private val binding: AdapterBinding,
    private val isRounding: Boolean,
    private val onClick: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    private var hit: Hit? = null

    init {
        itemView.setOnThrottleClickListener {
            hit?.let { i ->
                onClick.invoke(i.largeImageURL)
            }
        }
        if (isRounding) {
            binding.largeImageRound.setVisible()
            binding.largeImage.setGone()
        } else {
            binding.largeImageRound.setGone()
            binding.largeImage.setVisible()
        }
    }

    fun onBinding(hit: Hit) {
        this.hit = hit
        if (hit.largeImageURL.isNotEmpty()) {
            Picasso.get().load(hit.largeImageURL).into(
                if (isRounding) {
                    binding.largeImageRound
                } else {
                    binding.largeImage
                }
            )
        }
    }
}