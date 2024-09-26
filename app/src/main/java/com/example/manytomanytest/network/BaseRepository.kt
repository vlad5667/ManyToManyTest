package com.example.manytomanytest.network

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.http.HttpStatusCode

open class BaseRepository {

    suspend fun <T> request(call: suspend () -> T): State<T> = try {
        State.Success(call())
    } catch (exception: Throwable) {
        when (exception) {
            is ClientRequestException -> State.Failure(
                statusCode = exception.response.status,
                exception = exception
            )
            is ServerResponseException -> State.Failure(
                statusCode = exception.response.status,
                exception = exception
            )
            else -> State.Failure(
                statusCode = HttpStatusCode.fromValue(-1), // unknown error
                exception = exception
            )
        }
    }
}