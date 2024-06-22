package com.example.androidtemplateapp.domain.impl

import com.example.androidtemplateapp.data.repository.UserDataRepository
import com.example.androidtemplateapp.domain.UserDataUseCase
import com.example.androidtemplateapp.entity.enums.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class UserDataUseCaseImpl(private val userDataRepository: UserDataRepository) : UserDataUseCase {

    override suspend fun getAppTheme(): Flow<AppTheme> =
        userDataRepository.getAppTheme().map { currentAppTheme ->
            currentAppTheme?.let {
                AppTheme.valueOf(currentAppTheme)
            } ?: run {
                AppTheme.AUTO
            }
        }


    override suspend fun setAppTheme(appTheme: AppTheme) {
        userDataRepository.setAppTheme(appTheme.name)
    }
}