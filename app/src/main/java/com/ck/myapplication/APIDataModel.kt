package com.ck.myapplication

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class APIDataModel (val totalHits: Int, val hits: ArrayList<Hit>): Parcelable