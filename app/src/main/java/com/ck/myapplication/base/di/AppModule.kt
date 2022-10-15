package com.ck.myapplication.base.di

import android.content.Context
import com.ck.myapplication.base.repository.network.FlowErrorHandlingCallAdapterFactory
import com.ck.myapplication.sample.network.APIConstants.Companion.SERVICE_ENDPOINT
import com.ck.myapplication.sample.network.TestAPIs
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context) =
        context.getSharedPreferences("MyApp", Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun providePicasso() = Picasso.get()

    @DispatcherMain
    @Singleton
    @Provides
    fun provideDispatcherMain(): CoroutineDispatcher {
        return Dispatchers.Main
    }

    @DispatcherIO
    @Singleton
    @Provides
    fun provideDispatcherIO(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @DispatcherDefault
    @Singleton
    @Provides
    fun provideDispatcherDefault(): CoroutineDispatcher {
        return Dispatchers.Default
    }

    @Singleton
    @Provides
    fun provideService() = Retrofit.Builder()
        .client(
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .build()
        )
        .baseUrl("$SERVICE_ENDPOINT/")
        .addCallAdapterFactory(FlowErrorHandlingCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()
        .create(TestAPIs::class.java)
}