package com.ck.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
): ViewModel() {
    private val squaresListLiveData = MutableLiveData<ArrayList<DataModel>>()

    init {
        viewModelScope.launch(context = Dispatchers.IO) {
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
            squaresListLiveData.postValue(list)
        }
    }

    fun getSquaresList(): MutableLiveData<ArrayList<DataModel>> {
        return squaresListLiveData
    }
//
//    fun updateSquaresList(list: ArrayList<DataModel>) {
//        squaresListLiveData.postValue(list)
//    }
}