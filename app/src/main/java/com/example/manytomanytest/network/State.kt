package com.example.manytomanytest.network

import io.ktor.http.HttpStatusCode
import java.io.IOException

sealed class State<out T> {

    data class Success<T>(val data: T?) : State<T>()

    data class Failure<T>(val statusCode: HttpStatusCode, val exception: Throwable?) : State<T>() {

        val isBadRequestError get() = statusCode == HttpStatusCode.BadRequest
        val isAuthorizationError get() = statusCode == HttpStatusCode.Unauthorized
        val isNotFoundError get() = statusCode == HttpStatusCode.NotFound
        val isNotAcceptableError get() = statusCode == HttpStatusCode.NotAcceptable
        val isTooManyRequestsError get() = statusCode == HttpStatusCode.TooManyRequests
        val isServerError get() = statusCode == HttpStatusCode.InternalServerError
        val isOfflineError get() = statusCode == HttpStatusCode.ServiceUnavailable
        val isNetworkError get() = exception is IOException

        val errorMessage: String
            get() = when {
                isBadRequestError -> "Oops, bad request"
                isAuthorizationError -> "Error, looks like you're not authorized"
                isNotFoundError -> "Sorry, looks like the content you're trying to reach doesn't exist"
                isNotAcceptableError -> "Incorrect format"
                isTooManyRequestsError -> "Sorry, there are too many requests, please try again later"
                isServerError -> "Server error. Sorry, we're already working on it"
                isOfflineError -> "Sorry, the server is temporarily down. Please wait until we finish server maintenance"
                isNetworkError -> "Ouch, it seems there is no internet connection"
                else -> "Unknown error"
            }
    }

    object Loading : State<Nothing>()
}
