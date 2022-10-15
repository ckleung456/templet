package com.ck.myapplication.sample.usecase

import android.os.Parcelable
import com.ck.myapplication.base.di.DispatcherIO
import com.ck.myapplication.base.di.DispatcherMain
import com.ck.myapplication.base.usecase.FlowUseCase
import com.ck.myapplication.sample.model.Hit
import com.ck.myapplication.sample.repository.FetchPixaBayInteractor
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class GetPixaBayUseCase @Inject constructor(
    @DispatcherMain private val mainDispatcher: CoroutineDispatcher,
    @DispatcherIO private val ioDispatcher: CoroutineDispatcher,
    private val interactor: FetchPixaBayInteractor
) : FlowUseCase<Unit, GetPixaBayUseCase.Output>(dispatcherMain = mainDispatcher) {

    override suspend fun getFlow(input: Unit): Flow<Output> = interactor
        .getPixaBay()
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
        .flowOn(ioDispatcher)

    @Parcelize
    data class Output(
        val totalHits: Int,
        val hitsImage: List<Hit>
    ) : Parcelable
}