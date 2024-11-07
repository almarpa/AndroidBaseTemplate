package com.example.androidtemplateapp.domain

import arrow.core.Either
import com.example.androidtemplateapp.common.errorhandler.entity.AppError
import com.example.androidtemplateapp.entity.PokemonDetails

interface PokemonDetailsUseCase {

    suspend fun getPokemonDetails(pokemonID: Int): Either<AppError, PokemonDetails>
}
