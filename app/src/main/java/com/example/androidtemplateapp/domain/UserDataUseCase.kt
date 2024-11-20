package com.example.androidtemplateapp.domain

import com.example.androidtemplateapp.entity.UserData
import com.example.androidtemplateapp.entity.enums.AppTheme
import kotlinx.coroutines.flow.Flow

interface UserDataUseCase {

    fun getUserData(): Flow<UserData>
    suspend fun setAppLocale(locale: String)
    suspend fun setAppTheme(appTheme: AppTheme)
}