package com.example.androidtemplateapp.domain

import com.example.androidtemplateapp.entity.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonUseCase {

    suspend fun getPokemons(): Flow<List<Pokemon>>

    suspend fun getTeamMembers(): Flow<List<Pokemon>>

    suspend fun addPokemonToTeam(pokemon: Pokemon)

    suspend fun searchPokemonsByName(name: String): Flow<List<Pokemon>>

    suspend fun createTeamMember(pokemon: Pokemon)
}
