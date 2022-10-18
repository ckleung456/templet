package com.ck.core.usecase

import com.ck.core.repository.network.RetrofitException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

interface UseCase<in INPUT, out RESULT> {
    operator fun invoke(
        input: INPUT,
        onResultFn: (RESULT) -> Unit = {}
    )
}

interface CoroutineUseCase<in INPUT, out RESULT> {
    suspend operator fun invoke(
        input: INPUT,
        onResultFn: (RESULT) -> Unit = {}
    )
}

abstract class FlowUseCase<in INPUT, out RESULT> {
    /**
     * Implement this in use case
     */
    protected abstract suspend fun getFlow(
        input: INPUT
    ): Flow<RESULT>

    /**
     * Implement this method if you need more logic to handle your error and results different RESULT type
     */
    protected open suspend fun errorResult(error: Throwable): RESULT? = null

    protected open fun downstreamThread(): CoroutineDispatcher = Dispatchers.Main

    /**
     * Call this in view model
     */
    suspend operator fun invoke(
        input: INPUT,
        onResultFn: (UseCaseOutputWithStatus<RESULT>) -> Unit
    ) = getFlow(input)
        .onStart {
            onResultFn(UseCaseOutputWithStatus.Progress())
        }
        .onEach { result ->
            onResultFn(UseCaseOutputWithStatus.Success(result = result))
        }
        .catch { e ->
            onResultFn(
                UseCaseOutputWithStatus.Failed(
                    error = if (e is RetrofitException) e else RetrofitException.unexpectedError(
                        exception = e
                    ),
                    failedResult = errorResult(error = e)
                )
            )
        }
        .flowOn(downstreamThread())
        .collect()
}

sealed class UseCaseOutputWithStatus<out RESULT> {
    data class Progress<out RESULT>(val data: Any? = null) : UseCaseOutputWithStatus<RESULT>()
    data class Success<out RESULT>(val result: RESULT) : UseCaseOutputWithStatus<RESULT>()
    data class Failed<out RESULT>(val error: RetrofitException, val failedResult: RESULT? = null) :
        UseCaseOutputWithStatus<RESULT>()
}