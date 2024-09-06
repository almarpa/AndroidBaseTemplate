package com.example.androidtemplateapp.common.errorhandler.entity

import java.io.Serializable

/**
 * It represents an Exception in the App
 * @property type The exception type
 * @property description the exception description
 * @param cause the cause of the error
 */
class AppError(
    val type: AppErrorType = AppErrorType.Unknown,
    val data: AppErrorData? = null,
    override val cause: Throwable? = null,
) : Exception(data?.getFormatted(), cause), Serializable