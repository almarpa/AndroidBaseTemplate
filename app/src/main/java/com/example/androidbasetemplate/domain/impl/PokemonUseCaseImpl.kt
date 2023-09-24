package com.example.androidbasetemplate.domain.impl

import com.example.androidbasetemplate.data.repository.PokemonRepository
import com.example.androidbasetemplate.domain.PokemonUseCase
import com.example.androidbasetemplate.entity.Pokemon
import com.example.androidbasetemplate.entity.PokemonDetail
import kotlinx.coroutines.flow.Flow

class PokemonUseCaseImpl(private val pokemonRepository: PokemonRepository) : PokemonUseCase {

    override suspend fun getPokemons(): Flow<List<Pokemon>> {
        return pokemonRepository.getPokemons()
    }

    override suspend fun getPokemon(pokemonUrl: String): PokemonDetail {
        return pokemonRepository.getPokemon(pokemonUrl)
    }
}
