package com.example.androidtemplateapp.data.repository

import arrow.core.Either
import com.example.androidtemplateapp.common.errorhandler.entity.AppError
import com.example.androidtemplateapp.entity.PokemonDetails

interface PokemonDetailsRepository {

    suspend fun getPokemonDetails(pokemonID: Int): Either<AppError, PokemonDetails>
}
