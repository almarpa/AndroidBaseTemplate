package com.example.androidtemplateapp.domain

import androidx.paging.PagingData
import com.example.androidtemplateapp.entity.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonUseCase {

    fun getPokemons(pageSize: Int): Flow<PagingData<Pokemon>>

    fun getTeamMembers(): Flow<List<Pokemon>>

    fun searchPokemonByName(name: String): Flow<List<Pokemon>>

    suspend fun addPokemonToTeam(pokemon: Pokemon)

    suspend fun createPokemonMember(pokemon: Pokemon)
}
