package com.example.androidtemplateapp.common.utils

import com.example.androidtemplateapp.common.errorhandler.entity.AppErrorType

sealed interface Resource<T> {
    data class Success<T>(val data: T) : Resource<T>
    data class Error<T>(val error: AppErrorType) : Resource<T>
    class Loading<T> : Resource<T>
}
