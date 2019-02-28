package com.ck.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class SquaresAdapter: ListAdapter<DataModel, SquareViewHolder>(SquaresDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SquareViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter, parent, false))

    override fun onBindViewHolder(holder: SquareViewHolder, position: Int) {
        holder.binding(getItem(position))
    }

    override fun onBindViewHolder(holder: SquareViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else if (payloads[payloads.size-1] is DataModel) {
            val newItem = payloads[payloads.size-1] as DataModel
            holder.binding(newItem)
        }
    }
}