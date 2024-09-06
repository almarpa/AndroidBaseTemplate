package com.example.androidtemplateapp.common.utils

import com.example.androidtemplateapp.common.errorhandler.entity.AppError

sealed interface Result<T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error<T>(val error: AppError) : Result<T>
}
