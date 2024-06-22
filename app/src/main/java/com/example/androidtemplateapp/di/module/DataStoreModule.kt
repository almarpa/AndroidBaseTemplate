package com.example.androidtemplateapp.di.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.androidtemplateapp.data.db.datastore.DataStoreSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    companion object {
        const val DATA_STORE_FILE = "DATA_STORE_FILE"
    }

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext application: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { application.preferencesDataStoreFile(DATA_STORE_FILE) }
        )
    }

    @Singleton
    @Provides
    fun provideDataStoreSource(dataStore: DataStore<Preferences>): DataStoreSource {
        return DataStoreSource(dataStore)
    }
}