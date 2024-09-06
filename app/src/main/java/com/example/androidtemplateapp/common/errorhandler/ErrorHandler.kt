package com.example.androidtemplateapp.common.errorhandler

import com.example.androidtemplateapp.common.errorhandler.entity.AppError
import com.example.androidtemplateapp.common.errorhandler.entity.AppErrorData
import com.example.androidtemplateapp.common.errorhandler.entity.AppErrorType
import retrofit2.Response
import javax.inject.Singleton

@Singleton
object ErrorHandler {

    fun processError(response: Response<out Any>): AppError {
        return getAppError(response)
    }

    private fun getAppError(response: Response<out Any>): AppError {
        val errorType = when (response.code()) {
            in 200..299 -> AppErrorType.MalformedResponse
            in 401..403 -> AppErrorType.Api.Forbidden
            404 -> AppErrorType.Api.NotFound
            in 400..499 -> AppErrorType.Api.MalformedRequest
            in 500..599 -> AppErrorType.Api.InternalServerError
            else -> AppErrorType.Unknown
        }

        return AppError(
            type = errorType,
            data = response.errorBody()?.let { body ->
                AppErrorData(
                    code = response.code().toString(),
                    detail = body.string()
                )
            }
        )
    }
}