package com.example.androidbasetemplate.domain

import com.example.androidbasetemplate.entity.PokemonDetails
import kotlinx.coroutines.flow.Flow

interface PokemonDetailsUseCase {

    suspend fun getPokemonDetails(pokemonID: Int): Flow<PokemonDetails>
}
