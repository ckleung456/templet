package com.ck.myapplication.sample.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class SampleActivityViewStateModel : Parcelable {
    @Parcelize
    object Picker : SampleActivityViewStateModel()

    @Parcelize
    object SquareList : SampleActivityViewStateModel()

    @Parcelize
    data class ApiPhotoList(
        val isRounding: Boolean
    ) : SampleActivityViewStateModel()
}
