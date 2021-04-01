package com.aditprayogo.bfaa_submission2.core.util

import com.aditprayogo.bfaa_submission2.core.state.ResultState
import com.bumptech.glide.load.HttpException
import java.io.IOException
import java.net.ConnectException

suspend fun <T : Any> safeApiCall(apiCall: suspend () -> ResultState<T>): ResultState<T> {
    return try {
        apiCall()
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> ResultState.NetworkError
            is HttpException -> {
                val code = throwable.statusCode
                val errorMessage = throwable.message
                return ResultState.Error(errorMessage, code)
            }
            is ConnectException -> ResultState.NetworkError
            else -> ResultState.Error(null, 500)
        }
    }
}