package com.ck.myapplication.sample.usecase

import com.ck.myapplication.base.repository.network.RetrofitException
import com.ck.myapplication.base.usecase.UseCaseOutputWithStatus
import com.ck.myapplication.sample.model.Hit
import com.ck.myapplication.sample.network.APIDataModel
import com.ck.myapplication.sample.network.APIHit
import com.ck.myapplication.sample.repository.FetchPixaBayInteractor
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetPixaBayUseCaseTests {
    @MockK
    lateinit var interactorMock:  FetchPixaBayInteractor

    @MockK
    lateinit var onResultFnMock: (UseCaseOutputWithStatus<GetPixaBayUseCase.Output>) -> Unit

    @MockK
    lateinit var apiDataModelMock: APIDataModel

    @MockK
    lateinit var apiHitMock: APIHit

    @MockK
    lateinit var retrofitExceptionMock: RetrofitException

    private var underTests: GetPixaBayUseCase? = null

    @Before
    fun `set up`() {
        MockKAnnotations.init(this, relaxed = true)
        underTests = GetPixaBayUseCase(
            mainDispatcher = Dispatchers.Unconfined,
            ioDispatcher = Dispatchers.Unconfined,
            interactor = interactorMock
        )
    }

    @After
    fun `tear down`() {
        underTests = null
        unmockkAll()
    }

    @Test
    fun `invoke  successfully`() = runBlocking {
        //GIVEN
        val exceptedTotalHits = 100
        val exceptedId = 1L
        val exceptedUrl = "URL"
        every {
            interactorMock.getPixaBay()
        } returns flowOf(apiDataModelMock)

        every {
            apiDataModelMock.totalHits
        } returns exceptedTotalHits
        every {
            apiDataModelMock.hits
        } returns listOf(apiHitMock)
        every {
            apiHitMock.id
        } returns exceptedId
        every {
            apiHitMock.largeImageURL
        } returns exceptedUrl

        //WHEN
        underTests?.invoke(input = Unit, onResultFn = onResultFnMock)

        //THEN
        verify {
            onResultFnMock.invoke(UseCaseOutputWithStatus.Progress())
            onResultFnMock.invoke(UseCaseOutputWithStatus.Success(
                result = GetPixaBayUseCase.Output(
                    totalHits = exceptedTotalHits,
                    hitsImage = listOf(
                        Hit(
                            id = exceptedId,
                            largeImageURL = exceptedUrl
                        )
                    )
                )
            ))
        }
    }

    @Test
    fun `invoke  failed`() = runBlocking {
        //GIVEN
        every {
            interactorMock.getPixaBay()
        } returns flow {
            throw retrofitExceptionMock
        }

        //WHEN
        underTests?.invoke(input = Unit, onResultFn = onResultFnMock)

        //THEN
        verify {
            onResultFnMock.invoke(UseCaseOutputWithStatus.Progress())
            onResultFnMock.invoke(UseCaseOutputWithStatus.Failed(
                error = retrofitExceptionMock,
                failedResult = null
            ))
        }
    }
}