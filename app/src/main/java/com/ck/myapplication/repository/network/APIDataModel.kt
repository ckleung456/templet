package com.ck.myapplication.repository.network

import android.os.Parcelable
import com.ck.myapplication.Hit
import kotlinx.android.parcel.Parcelize

@Parcelize
data class APIDataModel (val totalHits: Int, val hits: ArrayList<Hit>): Parcelable