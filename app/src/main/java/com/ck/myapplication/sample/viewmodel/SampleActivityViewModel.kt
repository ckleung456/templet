package com.ck.myapplication.sample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ck.myapplication.base.utils.Event
import com.ck.myapplication.base.utils.fireEvent
import com.ck.myapplication.sample.model.SampleActivityViewStateModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SampleActivityViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _viewLiveState = MutableLiveData<Event<SampleActivityViewStateModel>>()
    val viewLiveState: LiveData<Event<SampleActivityViewStateModel>> = _viewLiveState

    init {
        _viewLiveState.fireEvent(SampleActivityViewStateModel.Picker)
    }

    fun onStateChanged(viewState: SampleActivityViewStateModel) {
        _viewLiveState.fireEvent(viewState)
    }
}