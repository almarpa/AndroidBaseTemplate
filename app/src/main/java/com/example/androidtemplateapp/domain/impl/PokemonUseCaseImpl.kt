package com.example.androidtemplateapp.domain.impl

import androidx.paging.PagingData
import com.example.androidtemplateapp.data.repository.PokemonRepository
import com.example.androidtemplateapp.domain.PokemonUseCase
import com.example.androidtemplateapp.entity.Pokemon
import kotlinx.coroutines.flow.Flow

class PokemonUseCaseImpl(private val pokemonRepository: PokemonRepository) : PokemonUseCase {

    override fun getPokemons(pageSize: Int): Flow<PagingData<Pokemon>> =
        pokemonRepository.getPokemons(pageSize)

    override fun getTeamMembers(): Flow<List<Pokemon>> =
        pokemonRepository.getTeamMembers()

    override fun searchPokemonByName(name: String): Flow<List<Pokemon>> =
        pokemonRepository.searchPokemonByName(name)

    override suspend fun addPokemonToTeam(pokemon: Pokemon) {
        pokemonRepository.addPokemonToTeam(pokemon)
    }

    override suspend fun createPokemonMember(pokemon: Pokemon) =
        pokemonRepository.createPokemonMember(pokemon)

    override suspend fun addPokemonDominantColor(pokemonId: Int, color: Int) =
        pokemonRepository.addPokemonDominantColor(pokemonId, color)
}
