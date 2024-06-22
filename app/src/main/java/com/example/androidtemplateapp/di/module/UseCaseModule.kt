package com.example.androidtemplateapp.di.module

import com.example.androidtemplateapp.data.repository.PokemonDetailsRepository
import com.example.androidtemplateapp.data.repository.PokemonRepository
import com.example.androidtemplateapp.data.repository.UserDataRepository
import com.example.androidtemplateapp.domain.PokemonDetailsUseCase
import com.example.androidtemplateapp.domain.PokemonUseCase
import com.example.androidtemplateapp.domain.UserDataUseCase
import com.example.androidtemplateapp.domain.impl.PokemonDetailsUseCaseImpl
import com.example.androidtemplateapp.domain.impl.PokemonUseCaseImpl
import com.example.androidtemplateapp.domain.impl.UserDataUseCaseImpl
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

    @Provides
    @Singleton
    fun providePokemonDetailsUseCase(
        pokemonDetailsRepository: PokemonDetailsRepository,
    ): PokemonDetailsUseCase = PokemonDetailsUseCaseImpl(pokemonDetailsRepository)

    @Provides
    @Singleton
    fun provideUserDataUseCase(
        userDataRepository: UserDataRepository,
    ): UserDataUseCase = UserDataUseCaseImpl(userDataRepository)
}
