package com.ck.myapplication.base.di

import android.content.Context
import com.ck.core.repository.network.FlowErrorHandlingCallAdapterFactory
import com.ck.myapplication.BuildConfig.SERVER_ENDPOINT
import com.ck.myapplication.sample.network.TestAPIs
import com.google.gson.GsonBuilder
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
    fun provideService(
        flowErrorHandlingCallAdapterFactory: FlowErrorHandlingCallAdapterFactory
    ) = Retrofit.Builder()
        .client(
            OkHttpClient.Builder().apply {
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//                addInterceptor {  chain ->
//                    //Add necessary general headers here
//                    val requestBuilder = chain.request().newBuilder()
//                    requestBuilder.addHeader("", "")
//                    chain.proceed(requestBuilder.build())
//                }
            }
                .build()
        )
        .baseUrl("$SERVER_ENDPOINT/")
        .addCallAdapterFactory(flowErrorHandlingCallAdapterFactory)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()
        .create(TestAPIs::class.java)
}