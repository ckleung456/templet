package com.ck.myapplication.sample.viewmodel

import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.ck.myapplication.sample.CoroutineTestRule
import com.ck.myapplication.sample.model.SampleActivityViewStateModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@kotlinx.coroutines.ExperimentalCoroutinesApi
class SampleActivityViewModelTests {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    @MockK
    lateinit var savedStateHandleMock: SavedStateHandle

    @MockK
    lateinit var looperMock: Looper

    @MockK
    lateinit var viewStateModelMock: SampleActivityViewStateModel

    private var underTests: SampleActivityViewModel? = null

    @Before
    fun `set up`() {
        MockKAnnotations.init(this, relaxed = true)
        mockkStatic(Looper::class)
        every {
            Looper.myLooper()
        } returns looperMock
        every {
            Looper.getMainLooper()
        } returns looperMock
        underTests = SampleActivityViewModel(
            savedStateHandle = savedStateHandleMock
        )
    }

    @After
    fun `tear down`() {
        underTests = null
        unmockkAll()
    }

    @Test
    fun `init`() {
        //GIVEN & WHEN
        underTests?.viewLiveState?.observeForever {  }

        //THEN
        assertEquals(
            SampleActivityViewStateModel.Picker,
            underTests?.viewLiveState?.value?.peekContent()
        )
    }

    @Test
    fun `on state changed`() {
        //GIVEN
        underTests?.viewLiveState?.observeForever {  }
        val exceptedViewState = viewStateModelMock

        //WHEN
        underTests?.onStateChanged(viewState = viewStateModelMock)

        //THEN
        assertEquals(
            exceptedViewState,
            underTests?.viewLiveState?.value?.peekContent()
        )
    }
}