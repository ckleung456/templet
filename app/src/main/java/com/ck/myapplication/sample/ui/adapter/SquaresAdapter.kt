package com.ck.myapplication.sample.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ck.myapplication.databinding.AdapterBinding
import com.ck.myapplication.sample.model.DataModel
import com.ck.myapplication.sample.ui.viewholder.SquareViewHolder

class SquaresAdapter : ListAdapter<DataModel, SquareViewHolder>(SquaresDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SquareViewHolder(
        binding = AdapterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: SquareViewHolder, position: Int) {
        holder.binding(getItem(position))
    }

    override fun onBindViewHolder(
        holder: SquareViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else if (payloads[payloads.size - 1] is DataModel) {
            val newItem = payloads[payloads.size - 1] as DataModel
            holder.binding(newItem)
        }
    }
}