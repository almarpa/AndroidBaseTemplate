package com.example.androidbasetemplate.domain.mock

import com.example.androidbasetemplate.domain.PokemonDetailsUseCase
import com.example.androidbasetemplate.entity.PokemonDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class PokemonDetailsUseCaseImplMock : PokemonDetailsUseCase {

    override suspend fun getPokemonDetails(pokemonID: Int): Flow<PokemonDetails> {
        return flowOf()
    }
}
