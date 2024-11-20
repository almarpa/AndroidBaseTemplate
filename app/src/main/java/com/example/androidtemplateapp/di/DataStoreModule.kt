package com.example.androidtemplateapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.androidtemplateapp.data.db.datastore.DataStoreSource
import com.example.androidtemplateapp.data.db.datastore.impl.DataStoreSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    companion object {
        const val DATA_STORE_FILE = "DATA_STORE_FILE"
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_FILE)

    @Provides
    @Singleton
    fun provideDataStoreSource(
        @ApplicationContext context: Context,
    ): DataStoreSource =
        DataStoreSourceImpl(context.dataStore)
}