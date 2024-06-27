package com.example.androidtemplateapp.domain.impl

import com.example.androidtemplateapp.data.repository.PokemonRepository
import com.example.androidtemplateapp.domain.PokemonUseCase
import com.example.androidtemplateapp.entity.Pokemon
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

    override suspend fun createPokemonMember(pokemon: Pokemon) =
        pokemonRepository.createPokemonMember(pokemon)

}
