package com.example.androidtemplateapp.domain.mock

import com.example.androidtemplateapp.domain.PokemonDetailsUseCase
import com.example.androidtemplateapp.entity.PokemonDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class PokemonDetailsUseCaseImplMock : PokemonDetailsUseCase {

    override suspend fun getPokemonDetails(pokemonID: Int): Flow<PokemonDetails> {
        return flowOf()
    }
}
