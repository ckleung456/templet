package com.ck.myapplication.sample.network

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface TestAPIs {
    @GET(APIConstants.API_ENDPONT)
    fun fetchAPI(): Flow<APIDataModel>
}