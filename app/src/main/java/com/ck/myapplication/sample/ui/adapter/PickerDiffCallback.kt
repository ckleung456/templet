package com.ck.myapplication.sample.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.ck.myapplication.sample.model.SampleActivityViewStateModel

class PickerDiffCallback constructor(
    private val oldList: List<SampleActivityViewStateModel>,
    private val newList: List<SampleActivityViewStateModel>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}