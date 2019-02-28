package com.ck.myapplication

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIService private constructor(retrofit: Retrofit) {
    val service = retrofit.create(TestAPIs::class.java)
    private object Holder {
        private val httpBuilder = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        private val gson = GsonBuilder().setLenient().create()
        val apiService = APIService(
            Retrofit.Builder()
                .client(httpBuilder.build())
                .baseUrl(APIConstants.SERVICE_ENDPOINT.plus("/"))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        )
    }

    companion object {
        val INSTANCE: APIService by lazy { Holder.apiService }
    }
}