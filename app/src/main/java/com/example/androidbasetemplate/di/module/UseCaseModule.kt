package com.example.androidbasetemplate.di.module

import com.example.androidbasetemplate.data.repository.PokemonRepository
import com.example.androidbasetemplate.data.repository.UserDataRepository
import com.example.androidbasetemplate.domain.PokemonUseCase
import com.example.androidbasetemplate.domain.UserDataUseCase
import com.example.androidbasetemplate.domain.impl.PokemonUseCaseImpl
import com.example.androidbasetemplate.domain.impl.UserDataUseCaseImpl
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
    fun provideUserDataUseCase(
        userDataRepository: UserDataRepository,
    ): UserDataUseCase = UserDataUseCaseImpl(userDataRepository)
}
