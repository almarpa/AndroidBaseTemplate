package com.example.androidbasetemplate.domain.mock

import com.example.androidbasetemplate.domain.PokemonUseCase
import com.example.androidbasetemplate.entity.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class PokemonUseCaseImplMock : PokemonUseCase {

    override suspend fun getPokemons(): Flow<List<Pokemon>> {
        return flowOf()
    }

    override suspend fun getTeamMembers(): Flow<List<Pokemon>> {
        return flowOf()
    }

    override suspend fun addPokemonToTeam(pokemon: Pokemon) {}

    override suspend fun searchPokemonsByName(name: String): Flow<List<Pokemon>> {
        return flowOf()
    }
}
