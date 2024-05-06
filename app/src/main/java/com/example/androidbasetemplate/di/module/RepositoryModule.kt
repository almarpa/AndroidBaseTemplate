package com.example.androidbasetemplate.di.module

import com.example.androidbasetemplate.data.db.database.dao.PokemonDao
import com.example.androidbasetemplate.data.db.datastore.DataStoreSource
import com.example.androidbasetemplate.data.db.ws.api.PokemonApi
import com.example.androidbasetemplate.data.repository.PokemonRepository
import com.example.androidbasetemplate.data.repository.UserDataRepository
import com.example.androidbasetemplate.data.repository.impl.PokemonRepositoryImpl
import com.example.androidbasetemplate.data.repository.impl.UserDataRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providePokemonRepository(
        pokemonApi: PokemonApi,
        pokemonDao: PokemonDao,
    ): PokemonRepository {
        return PokemonRepositoryImpl(pokemonApi, pokemonDao)
    }

    @Provides
    @Singleton
    fun provideUserDataRepository(
        dataStoreSource: DataStoreSource,
    ): UserDataRepository {
        return UserDataRepositoryImpl(dataStoreSource)
    }
}
