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

//val operationError = OperationError
//    .Builder(OperationErrorType.CONNECTION_ERROR)
//    .errorId(1)
//    .messageTitle("Connection Error")
//    .message("Device is not connected to the internet. Please check your mobile internet connection.")
//    .build()
//
//val timeoutError = OperationError
//    .Builder(OperationErrorType.TIMEOUT_ERROR)
//    .errorId(2)
//    .messageTitle("Connection Error")
//    .message("Failed to connect to the server please try again later.")
//    .build()

