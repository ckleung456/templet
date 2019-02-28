package com.ck.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel: ViewModel() {
    private val squaresListLiveData = MutableLiveData<ArrayList<DataModel>>()

    fun getSquaresList(): MutableLiveData<ArrayList<DataModel>> {
        return squaresListLiveData
    }

    fun updateSquaresList(list: ArrayList<DataModel>) {
        squaresListLiveData.postValue(list)
    }
}