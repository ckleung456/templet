package com.ck.myapplication.sample.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.ck.myapplication.R
import com.ck.myapplication.base.usecase.UseCaseOutputWithStatus
import com.ck.myapplication.sample.model.DataModel
import com.ck.myapplication.sample.model.Hit
import com.ck.myapplication.sample.model.SampleActivityViewStateModel
import com.ck.myapplication.sample.usecase.GetPixaBayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getPixaBayUseCase: GetPixaBayUseCase
) : ViewModel() {
    companion object {
        private val TAG = "MyViewModel"
        val ARGUMENT_PICK_STATE = "$TAG.ARGUMENT_PICK_STATE"
    }

    val state = savedStateHandle.get<SampleActivityViewStateModel>(ARGUMENT_PICK_STATE)

    private val _squaresListLiveData = MutableLiveData<ArrayList<DataModel>>()
    val squaresListLiveData: LiveData<ArrayList<DataModel>> = _squaresListLiveData
    private val _hitListLiveData = MutableLiveData<List<Hit>>()
    val hitListLiveData: LiveData<List<Hit>> = _hitListLiveData

    init {
        viewModelScope.launch {
            if (state == SampleActivityViewStateModel.SquareList) {
                withContext(context = Dispatchers.IO) {
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
                    _squaresListLiveData.postValue(list)
                }
            } else if (state  is SampleActivityViewStateModel.ApiPhotoList) {
                getPixaBayUseCase
                    .invoke(
                        input = "tiger"
                    ) { state ->
                        when (state) {
                            is UseCaseOutputWithStatus.Progress -> {}
                            is UseCaseOutputWithStatus.Failed -> {
                                val error = state.error
                                Log.e(TAG, error.message, error)
                            }
                            is UseCaseOutputWithStatus.Success -> {
                                _hitListLiveData.value = state.result.hitsImage
                            }
                        }
                    }
            }
        }
    }
}