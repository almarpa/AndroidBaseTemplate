package com.example.androidtemplateapp.common.errorhandler

import com.example.androidtemplateapp.common.errorhandler.entity.AppError
import com.example.androidtemplateapp.common.errorhandler.entity.AppErrorData
import com.example.androidtemplateapp.common.errorhandler.entity.AppErrorType
import retrofit2.Response
import java.io.IOException
import java.io.InterruptedIOException
import java.net.MalformedURLException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.net.UnknownServiceException
import java.util.concurrent.TimeoutException
import javax.inject.Singleton
import javax.net.ssl.SSLException

@Singleton
object ErrorHandler {

    fun processApiException(response: Response<out Any>): AppError {
        return getAppError(response)
    }

    fun processException(
        exception: Throwable,
        defaultErrorType: AppErrorType? = null,
    ): AppError =
        when (exception) {
            is AppError -> exception
            is Exception -> getAppError(exception, defaultErrorType)
            else -> AppError(
                type = AppErrorType.Unknown,
                cause = exception
            )
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

    private fun getAppError(
        exception: Throwable,
        defaultErrorCode: AppErrorType? = null,
    ): AppError =
        when (exception) {
            is TimeoutException -> {
                AppError(
                    type = AppErrorType.Api.Timeout,
                    cause = exception
                )
            }

            is IOException -> {
                processIOException(exception, defaultErrorCode)
            }

            else -> {
                AppError(
                    type = defaultErrorCode ?: AppErrorType.MalformedResponse,
                    cause = exception
                )
            }
        }
    
    private fun processIOException(
        ioe: IOException,
        defaultErrorCode: AppErrorType? = null,
    ): AppError =
        when (ioe) {
            is UnknownHostException -> {
                AppError(
                    type = AppErrorType.Api.Offline,
                    cause = ioe
                )
            }

            is UnknownServiceException -> {
                AppError(
                    type = AppErrorType.Api.UnknownService,
                    cause = ioe
                )
            }

            is SocketException -> {
                AppError(
                    type = AppErrorType.Api.SocketError,
                    cause = ioe
                )
            }

            is SSLException -> {
                AppError(
                    type = AppErrorType.Api.SSLError,
                    cause = ioe
                )
            }

            is InterruptedIOException -> {
                AppError(
                    type = if (ioe is SocketTimeoutException) {
                        AppErrorType.Api.Timeout
                    } else {
                        AppErrorType.Api.InterruptedIO
                    },
                    cause = ioe
                )
            }

            is MalformedURLException -> {
                AppError(
                    type = AppErrorType.Api.MalformedUrl,
                    cause = ioe
                )
            }

            else -> AppError(
                type = defaultErrorCode ?: AppErrorType.MalformedResponse,
                cause = ioe
            )
        }
}