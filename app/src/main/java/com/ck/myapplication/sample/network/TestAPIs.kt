package com.ck.myapplication.sample.network

import com.ck.myapplication.BuildConfig
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface TestAPIs {
    @GET(APIConstants.API_ENDPOINT)
    fun fetchAPI(
        @Query(APIConstants.QUERY_KEY) key: String = BuildConfig.SERVER_KEY,
        @Query(APIConstants.QUERY_ANIMAL_NAME) animalName: String,
        @Query(APIConstants.QUERY_PRETTY) pretty: Boolean = true,
        @Query(APIConstants.QUERY_IMAGE_TYPE) type: String = "photo"
    ): Flow<APIDataModel>
}