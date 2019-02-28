package com.ck.myapplication

import io.reactivex.Observable
import retrofit2.http.GET

interface TestAPIs {
    @GET(APIConstants.API_ENDPONT)
    fun fetchAPI(): Observable<APIDataModel>
}