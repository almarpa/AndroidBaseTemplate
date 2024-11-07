package com.example.androidtemplateapp.domain.impl

import arrow.core.Either
import com.example.androidtemplateapp.common.errorhandler.entity.AppError
import com.example.androidtemplateapp.data.repository.PokemonDetailsRepository
import com.example.androidtemplateapp.domain.PokemonDetailsUseCase
import com.example.androidtemplateapp.entity.PokemonDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonDetailsUseCaseImpl(private val pokemonDetailsRepository: PokemonDetailsRepository) :
    PokemonDetailsUseCase {

    override suspend fun getPokemonDetails(pokemonID: Int): Either<AppError, PokemonDetails> =
        withContext(Dispatchers.Default) {
            pokemonDetailsRepository.getPokemonDetails(pokemonID)
        }
}
