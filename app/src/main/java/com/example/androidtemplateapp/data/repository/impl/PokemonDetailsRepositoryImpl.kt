package com.example.androidtemplateapp.data.repository.impl

import arrow.core.Either
import com.example.androidtemplateapp.common.errorhandler.ErrorHandler
import com.example.androidtemplateapp.common.errorhandler.entity.AppError
import com.example.androidtemplateapp.data.db.database.dao.PokemonDetailsDao
import com.example.androidtemplateapp.data.db.ws.api.PokemonApi
import com.example.androidtemplateapp.data.repository.PokemonDetailsRepository
import com.example.androidtemplateapp.entity.PokemonDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonDetailsRepositoryImpl(
    private val pokemonApi: PokemonApi,
    private val pokemonDetailsDao: PokemonDetailsDao,
) : PokemonDetailsRepository {

    override suspend fun getPokemonDetails(pokemonID: Int): Either<AppError, PokemonDetails> =
        Either.catchOrThrow<Throwable, PokemonDetails> {
            getLocalPokemonDetails(pokemonID) ?: run {
                with(pokemonApi.getPokemon(pokemonID).execute()) {
                    return@with body()?.map()?.let { remoteDetails ->
                        savePokemonDetails(remoteDetails)
                        remoteDetails
                    } ?: run {
                        throw ErrorHandler.processResponseError(this)
                    }
                }
            }
        }.mapLeft { throwable ->
            ErrorHandler.processException(throwable)
        }

    private suspend fun getLocalPokemonDetails(pokemonID: Int): PokemonDetails? {
        return withContext(Dispatchers.IO) {
            pokemonDetailsDao.get(pokemonID.toString())?.asDomain()
        }
    }

    private suspend fun savePokemonDetails(pokemonDetails: PokemonDetails) {
        withContext(Dispatchers.IO) {
            pokemonDetailsDao.insert(pokemonDetails.asEntity())
        }
    }
}
