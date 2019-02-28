package com.ck.myapplication

import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MyPresenterImpl(private val viewModel: MyViewModel, private val service: ExecutorService): MyPresenter {
    constructor(viewModel: MyViewModel): this(viewModel, Executors.newSingleThreadExecutor())

    override fun onStart(owner: LifecycleOwner) {
        service.submit {
            val list = ArrayList<DataModel>()
            for (i in 1..1000) {
                val mod = i % 4
                val colorId = if (mod == 0) {
                    R.color.colorAccent
                } else if (mod == 1) {
                    R.color.colorPrimary
                } else if (mod == 2) {
                    R.color.colorPrimaryDark
                } else {
                    R.color.abc_color_highlight_material
                }
                list.add(DataModel(i, colorId))
            }
            viewModel.updateSquaresList(list)
        }
    }
}