package com.example.androidtemplateapp.domain.impl

import com.example.androidtemplateapp.common.utils.Result
import com.example.androidtemplateapp.data.repository.PokemonDetailsRepository
import com.example.androidtemplateapp.domain.PokemonDetailsUseCase
import com.example.androidtemplateapp.entity.PokemonDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PokemonDetailsUseCaseImpl(private val pokemonDetailsRepository: PokemonDetailsRepository) :
    PokemonDetailsUseCase {

    override suspend fun getPokemonDetails(pokemonID: Int): Flow<Result<PokemonDetails>> =
        withContext(Dispatchers.Default) {
            pokemonDetailsRepository.getPokemonDetails(pokemonID)
        }
}
