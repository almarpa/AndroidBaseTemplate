package com.example.androidbasetemplate.data.repository

import com.example.androidbasetemplate.entity.PokemonDetails
import kotlinx.coroutines.flow.Flow

interface PokemonDetailsRepository {

    suspend fun getPokemonDetails(pokemonID: Int): Flow<PokemonDetails>
}
