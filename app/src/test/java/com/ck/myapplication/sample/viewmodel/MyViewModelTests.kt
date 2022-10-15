package com.ck.myapplication.sample.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.ck.myapplication.R
import com.ck.myapplication.base.usecase.UseCaseOutputWithStatus
import com.ck.myapplication.sample.CoroutineTestRule
import com.ck.myapplication.sample.model.DataModel
import com.ck.myapplication.sample.model.Hit
import com.ck.myapplication.sample.model.SampleActivityViewStateModel
import com.ck.myapplication.sample.usecase.GetPixaBayUseCase
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@kotlinx.coroutines.ExperimentalCoroutinesApi
class MyViewModelTests {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    @MockK
    lateinit var savedStateHandleMock: SavedStateHandle

    @MockK
    lateinit var useCaseMock: GetPixaBayUseCase

    @MockK
    lateinit var hitMock: Hit

    private var underTests: MyViewModel? = null

    @Before
    fun `set up`() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @After
    fun `tear down`() {
        underTests = null
        unmockkAll()
    }

    @Test
    fun `square list`() = runTest {
        //GIVEN
        every {
            savedStateHandleMock.get<SampleActivityViewStateModel>(MyViewModel.ARGUMENT_PICK_STATE)
        } returns SampleActivityViewStateModel.SquareList
        val list = ArrayList<DataModel>()
        for (i in 1..1000) {
            val mod = i % 4
            val colorId = if (mod == 0) {
                R.color.colorAccent
            } else if (mod == 1) {
                R.color.colorPrimary
            } else if (mod == 2) {
                R.color.colorPrimaryDark
            } else {
                R.color.abc_color_highlight_material
            }
            list.add(DataModel(i, colorId))
        }

        //WHEN
        underTests = MyViewModel(
            savedStateHandle = savedStateHandleMock,
            getPixaBayUseCase = useCaseMock
        )
        underTests?.squaresListLiveData?.observeForever {
        }

        //THEN
        Thread.sleep(500L)
        assertEquals(
            list,
            underTests?.squaresListLiveData?.value
        )
    }

    @Test
    fun `api photo list`() = runTest {
        //GIVEN
        every {
            savedStateHandleMock.get<SampleActivityViewStateModel>(MyViewModel.ARGUMENT_PICK_STATE)
        } returns SampleActivityViewStateModel.ApiPhotoList(isRounding = false)
        val methodSlot = slot<(UseCaseOutputWithStatus<GetPixaBayUseCase.Output>) -> Unit>()
        val exceptedHitList = listOf(
            hitMock
        )
        coEvery {
            useCaseMock
                .invoke(
                    input = "tiger",
                    onResultFn = capture(methodSlot)
                )
        } answers {
            secondArg<(UseCaseOutputWithStatus<GetPixaBayUseCase.Output>)-> Unit>().invoke(
                UseCaseOutputWithStatus.Success(
                    result = GetPixaBayUseCase.Output(
                        totalHits = 100,
                        hitsImage = exceptedHitList
                    )
                )
            )
        }


        //WHEN
        underTests = MyViewModel(
            savedStateHandle = savedStateHandleMock,
            getPixaBayUseCase = useCaseMock
        )
        underTests?.hitListLiveData?.observeForever {
        }

        //THEN
        Thread.sleep(500L)
        assertEquals(
            exceptedHitList,
            underTests?.hitListLiveData?.value
        )
    }
}