package com.ck.myapplication

import android.os.Parcelable
import androidx.annotation.ColorRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataModel(val id: Int, @ColorRes val colorId: Int): Parcelable