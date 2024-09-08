package com.example.androidtemplateapp.common.errorhandler.entity

/**
 * Represents the error information for an [AppError]
 *
 * @property code HTTP error code or null
 * @property detail error description
 */
data class AppErrorData(
    val code: String? = null,
    val detail: String,
) {
    
    fun getFormatted() = "$code: $detail"
}