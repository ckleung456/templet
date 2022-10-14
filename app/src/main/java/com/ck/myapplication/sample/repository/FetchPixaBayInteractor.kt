package com.ck.myapplication.sample.repository

import com.ck.myapplication.sample.network.TestAPIs
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject


@ViewModelScoped
class FetchPixaBayInteractor @Inject constructor(
    private val service: TestAPIs
) {
    fun getPixaBay() = service.fetchAPI()
}