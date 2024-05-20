package com.example.androidbasetemplate.domain.impl

import com.example.androidbasetemplate.domain.UserDataUseCase
import com.example.androidbasetemplate.entity.enums.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


class FakeUserDataUseCaseImpl : UserDataUseCase {

    override suspend fun getAppTheme(): Flow<AppTheme> {
        return flowOf()
    }

    override suspend fun setAppTheme(appTheme: AppTheme) {}
}