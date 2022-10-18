package com.ck.myapplication.sample.usecase

import android.os.Parcelable
import com.ck.core.usecase.FlowUseCase
import com.ck.myapplication.sample.model.Hit
import com.ck.myapplication.sample.repository.FetchPixaBayInteractor
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class GetPixaBayUseCase @Inject constructor(
    private val interactor: FetchPixaBayInteractor
) : FlowUseCase<String, GetPixaBayUseCase.Output>() {

    override suspend fun getFlow(input: String): Flow<Output> = interactor
        .getPixaBay(
            animalName = input
        )
        .map {
            Output(
                totalHits = it.totalHits ?: 0,
                hitsImage = it.hits.orEmpty().map { apiHit ->
                    Hit(
                        id = apiHit.id ?: 0L,
                        largeImageURL = apiHit.largeImageURL ?: ""
                    )
                }
            )
        }
        .flowOn(Dispatchers.IO)

    @Parcelize
    data class Output(
        val totalHits: Int,
        val hitsImage: List<Hit>
    ) : Parcelable
}