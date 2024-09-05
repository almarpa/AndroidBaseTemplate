package com.example.androidtemplateapp.common.utils

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import com.example.androidtemplateapp.common.errorhandler.entity.AppErrorType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

@Suppress("DEPRECATION")
inline fun <reified T : Parcelable> Bundle.getParcelableCompat(code: String, clazz: Class<T>) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelable(code, clazz)
    } else {
        getParcelable(code)
    }

fun <T> Flow<T>.asResult(): Flow<Resource<T>> {
    return this
        .map<T, Resource<T>> { Resource.Success(it) }
        .onStart { emit(Resource.Loading()) }
        .catch { emit(Resource.Error(it.toAppErrorType())) }
}

fun Throwable.toAppErrorType(): AppErrorType {
    return AppErrorType.Unknown
}