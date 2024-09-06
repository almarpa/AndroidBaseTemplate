package com.example.androidtemplateapp.common.errorhandler.entity

/**
 * Represents the error information for an [AppError]
 *
 * @property code HTTP error code
 * @property detail error description
 */
data class AppErrorData(
    var code: String,
    var detail: String,
) {

    /**
     * Convert exception into loggable String from [AppErrorData]
     *
     * @return the formatted exception data
     */
    fun getFormatted() = "$code: $detail"
}