package com.ck.myapplication.sample.ui.viewholder

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ck.myapplication.databinding.AdapterBinding
import com.ck.myapplication.sample.model.DataModel

class SquareViewHolder(
    private val binding: AdapterBinding
) : RecyclerView.ViewHolder(binding.root) {
    private val context: Context = itemView.context

    fun binding(dataModel: DataModel) {
        binding.largeImage.setBackgroundColor(ContextCompat.getColor(context, dataModel.colorId))
    }
}