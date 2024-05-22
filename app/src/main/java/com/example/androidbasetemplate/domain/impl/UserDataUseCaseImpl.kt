package com.example.androidbasetemplate.domain.impl

import com.example.androidbasetemplate.data.repository.UserDataRepository
import com.example.androidbasetemplate.domain.UserDataUseCase
import com.example.androidbasetemplate.entity.enums.AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext


class UserDataUseCaseImpl(private val userDataRepository: UserDataRepository) : UserDataUseCase {

    override suspend fun getAppTheme(): Flow<AppTheme> =
        withContext(Dispatchers.Default) {
            userDataRepository.getAppTheme().map { currentAppTheme ->
                currentAppTheme?.let {
                    AppTheme.valueOf(currentAppTheme)
                } ?: run {
                    AppTheme.AUTO
                }
            }
        }

    override suspend fun setAppTheme(appTheme: AppTheme) {
        withContext(Dispatchers.Default) {
            userDataRepository.setAppTheme(appTheme.name)
        }
    }
}