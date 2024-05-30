package com.example.androidbasetemplate.data.repository

import com.example.androidbasetemplate.entity.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    suspend fun getPokemons(): Flow<List<Pokemon>>
    suspend fun getFavouritesPokemons(): Flow<List<Pokemon>>
    suspend fun savePokemonToFavourites(pokemon: Pokemon)
    suspend fun searchPokemonsByName(name: String): Flow<List<Pokemon>>
}
