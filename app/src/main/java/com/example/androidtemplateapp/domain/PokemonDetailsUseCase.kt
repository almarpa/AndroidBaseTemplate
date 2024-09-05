package com.example.androidtemplateapp.domain

import com.example.androidtemplateapp.common.utils.Resource
import com.example.androidtemplateapp.entity.PokemonDetails
import kotlinx.coroutines.flow.Flow

interface PokemonDetailsUseCase {

    suspend fun getPokemonDetails(pokemonID: Int): Flow<Resource<PokemonDetails>>
}
