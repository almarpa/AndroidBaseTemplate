package com.example.androidbasetemplate.domain

import com.example.androidbasetemplate.entity.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonUseCase {

    suspend fun getPokemons(): Flow<List<Pokemon>>

    suspend fun getTeamMembers(): Flow<List<Pokemon>>

    suspend fun addPokemonToTeam(pokemon: Pokemon)

    suspend fun searchPokemonsByName(name: String): Flow<List<Pokemon>>
}
