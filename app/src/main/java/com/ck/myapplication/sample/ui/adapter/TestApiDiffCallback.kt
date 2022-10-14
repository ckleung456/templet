package com.ck.myapplication.sample.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.ck.myapplication.sample.model.Hit

class TestApiDiffCallback(
    private val oldList: List<Hit>,
    private val newList: List<Hit>
): DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition].largeImageURL == newList[newItemPosition].largeImageURL
}