package com.ck.myapplication.sample.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ck.myapplication.databinding.AdapterPickerBinding
import com.ck.myapplication.sample.model.SampleActivityViewStateModel
import com.ck.myapplication.sample.ui.viewholder.PickerViewHolder

class PickerAdapter constructor(
    private val onClick: (SampleActivityViewStateModel) -> Unit
) : RecyclerView.Adapter<PickerViewHolder>() {
    var items = listOf<SampleActivityViewStateModel>()
        set(value) {
            val diffResult = DiffUtil.calculateDiff(
                PickerDiffCallback(
                    oldList = field,
                    newList = value
                )
            )
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PickerViewHolder =
        PickerViewHolder(
            binding = AdapterPickerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClick = onClick
        )

    override fun onBindViewHolder(holder: PickerViewHolder, position: Int) =
        holder.onBind(items[position])

    override fun getItemCount() = items.size
}