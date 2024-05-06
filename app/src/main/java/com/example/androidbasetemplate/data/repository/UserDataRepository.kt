package com.example.androidbasetemplate.data.repository

import kotlinx.coroutines.flow.Flow

interface UserDataRepository {

    suspend fun getAppTheme(): Flow<String?>
    suspend fun setAppTheme(isDarkTheme: String)
}