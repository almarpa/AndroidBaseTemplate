package com.example.androidbasetemplate.domain.impl

import com.example.androidbasetemplate.data.repository.PokemonDetailsRepository
import com.example.androidbasetemplate.domain.PokemonDetailsUseCase
import com.example.androidbasetemplate.entity.PokemonDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PokemonDetailsUseCaseImpl(private val pokemonDetailsRepository: PokemonDetailsRepository) :
    PokemonDetailsUseCase {

    override suspend fun getPokemonDetails(pokemonID: Int): Flow<PokemonDetails> =
        withContext(Dispatchers.Default) {
            pokemonDetailsRepository.getPokemonDetails(pokemonID)
        }
}
