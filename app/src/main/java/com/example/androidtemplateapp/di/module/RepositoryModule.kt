package com.example.androidtemplateapp.di.module

import com.example.androidtemplateapp.data.db.database.PokemonDataBase
import com.example.androidtemplateapp.data.db.database.dao.PokemonDao
import com.example.androidtemplateapp.data.db.database.dao.PokemonDetailsDao
import com.example.androidtemplateapp.data.db.datastore.DataStoreSource
import com.example.androidtemplateapp.data.db.ws.api.PokemonApi
import com.example.androidtemplateapp.data.repository.PokemonDetailsRepository
import com.example.androidtemplateapp.data.repository.PokemonRepository
import com.example.androidtemplateapp.data.repository.UserDataRepository
import com.example.androidtemplateapp.data.repository.impl.PokemonDetailsRepositoryImpl
import com.example.androidtemplateapp.data.repository.impl.PokemonRepositoryImpl
import com.example.androidtemplateapp.data.repository.impl.UserDataRepositoryImpl
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
        pokemonDataBase: PokemonDataBase,
    ): PokemonRepository {
        return PokemonRepositoryImpl(pokemonApi, pokemonDao, pokemonDataBase)
    }

    @Provides
    @Singleton
    fun providePokemonDetailsRepository(
        pokemonApi: PokemonApi,
        pokemonDetailsDao: PokemonDetailsDao,
    ): PokemonDetailsRepository {
        return PokemonDetailsRepositoryImpl(pokemonApi, pokemonDetailsDao)
    }

    @Provides
    @Singleton
    fun provideUserDataRepository(
        dataStoreSource: DataStoreSource,
    ): UserDataRepository {
        return UserDataRepositoryImpl(dataStoreSource)
    }
}
