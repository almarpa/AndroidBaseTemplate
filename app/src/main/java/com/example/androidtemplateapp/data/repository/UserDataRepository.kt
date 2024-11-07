package com.example.androidtemplateapp.data.repository

import kotlinx.coroutines.flow.Flow

interface UserDataRepository {

    fun getAppLocale(): Flow<String?>
    suspend fun setAppLocale(locale: String)
    fun getAppTheme(): Flow<String?>
    suspend fun setAppTheme(isDarkTheme: String)
}