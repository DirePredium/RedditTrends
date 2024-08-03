package com.direpredium.reddittrends.domain.models.api

typealias Mapper<Input, Output> = (Input) -> Output

sealed class AsyncResult<T> {

    fun <R> map(mapper: Mapper<T, R>? = null): AsyncResult<R> = when(this) {
        is PendingResult -> PendingResult()
        is ErrorResult -> ErrorResult(this.exception)
        is SuccessResult -> {
            if (mapper == null) throw IllegalArgumentException("Mapper should not be NULL for success result")
            SuccessResult(mapper(this.data))
        }
    }

}

class PendingResult<T> : AsyncResult<T>()

class SuccessResult<T>(
    val data: T
) : AsyncResult<T>()

class ErrorResult<T>(
    val exception: Exception
) : AsyncResult<T>()

fun <T> AsyncResult<T>?.takeSuccess(): T? {
    return if (this is SuccessResult)
        this.data
    else
        null
}