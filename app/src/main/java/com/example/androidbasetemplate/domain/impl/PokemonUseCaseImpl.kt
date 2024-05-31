package com.example.androidbasetemplate.domain.impl

import com.example.androidbasetemplate.data.repository.PokemonRepository
import com.example.androidbasetemplate.domain.PokemonUseCase
import com.example.androidbasetemplate.entity.Pokemon
import kotlinx.coroutines.flow.Flow

class PokemonUseCaseImpl(private val pokemonRepository: PokemonRepository) : PokemonUseCase {

    override suspend fun getPokemons(): Flow<List<Pokemon>> =
        pokemonRepository.getPokemons()


    override suspend fun getTeamMembers(): Flow<List<Pokemon>> =
        pokemonRepository.getTeamMembers()


    override suspend fun addPokemonToTeam(pokemon: Pokemon) {
        pokemonRepository.addPokemonToTeam(pokemon)
    }

    override suspend fun searchPokemonsByName(name: String): Flow<List<Pokemon>> =
        pokemonRepository.searchPokemonsByName(name)
    
}
