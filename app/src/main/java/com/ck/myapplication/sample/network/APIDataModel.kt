package com.ck.myapplication.sample.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class APIDataModel(val totalHits: Int?, val hits: List<APIHit>?) : Parcelable

@Parcelize
data class APIHit(
    val id: Long?,
    val pageURL: String?,
    val type: String?,
    val tags: String?,
    val previewURL: String?,
    val previewWidth: Int?,
    val previewHeight: Int?,
    val webformatURL: String?,
    val webformatWidth: Int?,
    val webformatHeight: Int?,
    val largeImageURL: String?,
    val imageWidth: Int?,
    val imageHeight: Int?,
    val imageSize: Long?,
    val views: Long?,
    val downloads: Long?,
    val collections: Long?,
    val likes: Long?,
    val comments: Long?,
    val user_id: Long?,
    val user: String?,
    val userImageURL: String?
) : Parcelable