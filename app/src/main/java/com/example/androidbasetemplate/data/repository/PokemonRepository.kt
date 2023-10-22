package com.example.androidbasetemplate.data.repository

import com.example.androidbasetemplate.entity.Pokemon
import com.example.androidbasetemplate.entity.PokemonDetail
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    suspend fun getPokemons(): Flow<List<Pokemon>>
    suspend fun getPokemon(pokemonUrl: String): PokemonDetail
}
