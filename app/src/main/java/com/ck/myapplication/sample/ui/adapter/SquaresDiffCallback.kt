package com.ck.myapplication.sample.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.ck.myapplication.sample.model.DataModel

class SquaresDiffCallback: DiffUtil.ItemCallback<DataModel>() {
    override fun areItemsTheSame(oldItem: DataModel, newItem: DataModel) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: DataModel, newItem: DataModel) = oldItem.colorId == newItem.colorId

    override fun getChangePayload(oldItem: DataModel, newItem: DataModel) = newItem
}