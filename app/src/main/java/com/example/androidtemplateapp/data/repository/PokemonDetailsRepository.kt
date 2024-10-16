package com.example.androidtemplateapp.data.repository

import com.example.androidtemplateapp.common.utils.Result
import com.example.androidtemplateapp.entity.PokemonDetails
import kotlinx.coroutines.flow.Flow

interface PokemonDetailsRepository {

    suspend fun getPokemonDetails(pokemonID: Int): Flow<Result<PokemonDetails>>
}
