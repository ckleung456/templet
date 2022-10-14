package com.ck.myapplication.sample.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Hit(
    val id: Long,
    val largeImageURL: String
) : Parcelable