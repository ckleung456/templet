package com.ck.myapplication.sample.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ck.myapplication.databinding.AdapterBinding
import com.ck.myapplication.sample.model.Hit
import com.ck.myapplication.sample.ui.viewholder.AdapterViewHolder

class TestApiAdapter constructor(
    private val onClick: (String) -> Unit
): RecyclerView.Adapter<AdapterViewHolder>() {
    var items = listOf<Hit>()
    set(value) {
        val diffResult = DiffUtil.calculateDiff(TestApiDiffCallback(
            oldList = field,
            newList = value
        ))
        field = value
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder = AdapterViewHolder(
        binding = AdapterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        onClick = onClick
    )

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) = holder.onBinding(items[position])

    override fun getItemCount() = items.size
}