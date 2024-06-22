package com.example.androidtemplateapp.domain.mock

import com.example.androidtemplateapp.domain.UserDataUseCase
import com.example.androidtemplateapp.entity.enums.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


class UserDataUseCaseImplMock : UserDataUseCase {

    override suspend fun getAppTheme(): Flow<AppTheme> {
        return flowOf()
    }

    override suspend fun setAppTheme(appTheme: AppTheme) {}
}