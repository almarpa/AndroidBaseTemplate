package com.example.androidtemplateapp.common.errorhandler.entity

sealed class AppErrorType {

    sealed class Api : AppErrorType() {

        data object InternalServerError : Api()

        data object Network : Api()

        data object ServiceUnavailable : Api()

        data object NotFound : Api()

        data object Timeout : Api()

        data object InterruptedIO : Api()

        data object Forbidden : Api()

        data object MalformedUrl : Api()

        data object MalformedRequest : Api()

        data object MalformedResponse : Api()

        data object UnknownService : Api()

        data object SocketError : Api()

        data object SSLError : Api()

        data object Server : Api()

        data object Offline : Api()
    }

    data object MalformedResponse : AppErrorType()
    data object Unknown : AppErrorType()
}
