package com.example.androidbasetemplate.di.module

import com.example.androidbasetemplate.data.repository.PokemonRepository
import com.example.androidbasetemplate.domain.PokemonUseCase
import com.example.androidbasetemplate.domain.impl.PokemonUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun providePokemonUseCase(
        pokemonRepository: PokemonRepository,
    ): PokemonUseCase = PokemonUseCaseImpl(pokemonRepository)
}
