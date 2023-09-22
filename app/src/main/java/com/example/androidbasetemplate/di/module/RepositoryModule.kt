package com.example.androidbasetemplate.di.module

import com.example.androidbasetemplate.data.db.dao.PokemonDao
import com.example.androidbasetemplate.data.db.ws.api.PokemonApi
import com.example.androidbasetemplate.data.repository.PokemonRepository
import com.example.androidbasetemplate.data.repository.impl.PokemonRepositoryImpl
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
}
