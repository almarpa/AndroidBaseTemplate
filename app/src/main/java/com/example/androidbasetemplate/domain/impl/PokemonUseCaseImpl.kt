package com.example.androidbasetemplate.domain.impl

import com.example.androidbasetemplate.data.repository.PokemonRepository
import com.example.androidbasetemplate.domain.PokemonUseCase
import com.example.androidbasetemplate.entity.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PokemonUseCaseImpl(private val pokemonRepository: PokemonRepository) : PokemonUseCase {

    override suspend fun getPokemons(): Flow<List<Pokemon>> =
        withContext(Dispatchers.Default) {
            pokemonRepository.getPokemons()
        }
}
