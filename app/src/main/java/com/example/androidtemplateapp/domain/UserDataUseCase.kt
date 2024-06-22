package com.example.androidtemplateapp.domain

import com.example.androidtemplateapp.entity.enums.AppTheme
import kotlinx.coroutines.flow.Flow

interface UserDataUseCase {

    suspend fun getAppTheme(): Flow<AppTheme>
    suspend fun setAppTheme(appTheme: AppTheme)
}