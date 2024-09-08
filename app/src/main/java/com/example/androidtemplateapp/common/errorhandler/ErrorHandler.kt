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

    fun processResponseError(response: Response<out Any>): AppError {
        return AppError(
            type = getAppErrorType(response.code()),
            data = response.errorBody()?.let { body ->
                AppErrorData(
                    code = response.code().toString(),
                    detail = body.string()
                )
            }
        )
    }

    fun processException(exception: Throwable): AppError =
        when (exception) {
            is Exception -> getAppError(exception)
            else -> getDefaultAppError(exception)
        }

    private fun getAppErrorType(httpErrorCode: Int): AppErrorType =
        when (httpErrorCode) {
            in 200..299 -> AppErrorType.MalformedResponse
            in 401..403 -> AppErrorType.Api.Forbidden
            404 -> AppErrorType.Api.NotFound
            in 400..499 -> AppErrorType.Api.MalformedRequest
            in 500..599 -> AppErrorType.Api.InternalServerError
            else -> AppErrorType.Unknown
        }

    private fun getAppError(exception: Throwable): AppError =
        when (exception) {
            is TimeoutException -> {
                AppError(
                    type = AppErrorType.Api.Timeout,
                    data = AppErrorData(detail = "Timeout exception"),
                    cause = exception
                )
            }

            is IOException -> {
                processIOException(exception)
            }

            else -> {
                AppError(
                    type = AppErrorType.MalformedResponse,
                    data = AppErrorData(detail = "Malformed response"),
                    cause = exception
                )
            }
        }

    private fun processIOException(ioException: IOException): AppError =
        when (ioException) {
            is UnknownHostException -> {
                AppError(
                    type = AppErrorType.Api.Offline,
                    data = AppErrorData(detail = "No Internet connection"),
                    cause = ioException
                )
            }

            is UnknownServiceException -> {
                AppError(
                    type = AppErrorType.Api.UnknownService,
                    data = AppErrorData(detail = "Unknown service exception"),
                    cause = ioException
                )
            }

            is SocketException -> {
                AppError(
                    type = AppErrorType.Api.SocketError,
                    data = AppErrorData(detail = "Socket exception"),
                    cause = ioException
                )
            }

            is SSLException -> {
                AppError(
                    type = AppErrorType.Api.SSLError,
                    data = AppErrorData(detail = "SSL exception"),
                    cause = ioException
                )
            }

            is InterruptedIOException -> {
                AppError(
                    type = if (ioException is SocketTimeoutException) {
                        AppErrorType.Api.Timeout
                    } else {
                        AppErrorType.Api.InterruptedIO
                    },
                    data = AppErrorData(detail = "Interrupted IO exception"),
                    cause = ioException
                )
            }

            is MalformedURLException -> {
                AppError(
                    type = AppErrorType.Api.MalformedUrl,
                    data = AppErrorData(detail = "Malformed URL exception"),
                    cause = ioException
                )
            }

            else -> AppError(
                type = AppErrorType.MalformedResponse,
                data = AppErrorData(detail = "Malformed response"),
                cause = ioException
            )
        }

    private fun getDefaultAppError(exception: Throwable) =
        AppError(
            type = AppErrorType.Unknown,
            data = AppErrorData(detail = "Unknown error"),
            cause = exception
        )
}