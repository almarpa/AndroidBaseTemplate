package com.example.androidbasetemplate.domain

import com.example.androidbasetemplate.entity.enums.AppTheme
import kotlinx.coroutines.flow.Flow

interface UserDataUseCase {

    suspend fun getAppTheme(): Flow<AppTheme>
    suspend fun setAppTheme(appTheme: AppTheme)
}