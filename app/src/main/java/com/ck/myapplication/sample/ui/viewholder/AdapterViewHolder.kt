package com.ck.myapplication.sample.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.ck.core.utils.loadImage
import com.ck.core.utils.setGone
import com.ck.core.utils.setOnSafeClickListener
import com.ck.core.utils.setVisible
import com.ck.myapplication.databinding.AdapterBinding
import com.ck.myapplication.sample.model.Hit

class AdapterViewHolder(
    private val binding: AdapterBinding,
    private val isRounding: Boolean,
    private val onClick: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    private var hit: Hit? = null

    init {
        itemView.setOnSafeClickListener {
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
            if (isRounding) {
                binding.largeImageRound
            } else {
                binding.largeImage
            }.loadImage(imageUri = hit.largeImageURL)
        }
    }
}