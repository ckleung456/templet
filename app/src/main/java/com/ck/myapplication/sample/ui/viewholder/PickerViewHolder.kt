package com.ck.myapplication.sample.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.ck.myapplication.R
import com.ck.myapplication.base.utils.setOnThrottleClickListener
import com.ck.myapplication.databinding.AdapterPickerBinding
import com.ck.myapplication.sample.model.SampleActivityViewStateModel

class PickerViewHolder constructor(
    private val binding: AdapterPickerBinding,
    private val onClick: (SampleActivityViewStateModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    private val context = itemView.context
    private var state: SampleActivityViewStateModel? = null

    init {
        binding.root.setOnThrottleClickListener {
            state?.let { state ->
                onClick.invoke(state)
            }
        }
    }

    fun onBind(state: SampleActivityViewStateModel) {
        this.state = state
        when (state) {
            is SampleActivityViewStateModel.SquareList -> binding.txtPickerName.text =
                context.getString(R.string.picker_square_list)
            is SampleActivityViewStateModel.ApiPhotoList -> binding.txtPickerName.text =
                if (state.isRounding) {
                    context.getString(R.string.picker_round_api_list)
                } else {
                    context.getString(R.string.picker_square_api_list)
                }
        }
    }
}