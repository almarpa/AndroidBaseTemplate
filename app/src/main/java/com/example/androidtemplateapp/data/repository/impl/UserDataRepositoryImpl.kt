package com.example.androidtemplateapp.data.repository.impl

import com.example.androidtemplateapp.data.db.datastore.DataStoreSource
import com.example.androidtemplateapp.data.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow


class UserDataRepositoryImpl(private val dataStoreSource: DataStoreSource) : UserDataRepository {

    private companion object {
        const val USER_APP_LOCALE = "user_app_locale"
        const val USER_APP_THEME = "user_app_theme"
    }

    override suspend fun getAppLocale(): Flow<String?> =
        dataStoreSource.getString(USER_APP_LOCALE)

    override suspend fun setAppLocale(locale: String) {
        dataStoreSource.putString(USER_APP_LOCALE, locale)
    }

    override suspend fun getAppTheme(): Flow<String?> =
        dataStoreSource.getString(USER_APP_THEME)

    override suspend fun setAppTheme(isDarkTheme: String) {
        dataStoreSource.putString(USER_APP_THEME, isDarkTheme)
    }
}
