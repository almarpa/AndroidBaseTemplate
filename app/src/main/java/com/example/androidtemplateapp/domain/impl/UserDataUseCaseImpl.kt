package com.example.androidtemplateapp.domain.impl

import com.example.androidtemplateapp.data.repository.UserDataRepository
import com.example.androidtemplateapp.domain.UserDataUseCase
import com.example.androidtemplateapp.entity.UserData
import com.example.androidtemplateapp.entity.enums.AppTheme
import com.example.androidtemplateapp.entity.enums.LocaleEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map


class UserDataUseCaseImpl(private val userDataRepository: UserDataRepository) : UserDataUseCase {

    override fun getUserData(): Flow<UserData> = combine(
        getAppLocale(),
        getAppTheme(),
    ) { locale: String, theme: AppTheme ->
        UserData(
            locale = locale,
            theme = theme
        )
    }
    
    override suspend fun setAppTheme(appTheme: AppTheme) {
        userDataRepository.setAppTheme(appTheme.name)
    }

    override suspend fun setAppLocale(locale: String) {
        userDataRepository.setAppLocale(locale)
    }

    private fun getAppTheme(): Flow<AppTheme> =
        userDataRepository.getAppTheme().map { currentAppTheme ->
            currentAppTheme?.let {
                AppTheme.valueOf(currentAppTheme)
            } ?: run {
                AppTheme.AUTO
            }
        }

    private fun getAppLocale(): Flow<String> =
        userDataRepository.getAppLocale().map { currentAppLocale ->
            currentAppLocale ?: LocaleEnum.EN.value
        }
}