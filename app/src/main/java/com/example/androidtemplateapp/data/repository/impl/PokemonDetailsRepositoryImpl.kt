package com.example.androidtemplateapp.data.repository.impl

import com.example.androidtemplateapp.common.errorhandler.ErrorHandler
import com.example.androidtemplateapp.common.errorhandler.entity.AppError
import com.example.androidtemplateapp.common.errorhandler.entity.AppErrorType
import com.example.androidtemplateapp.common.utils.Result
import com.example.androidtemplateapp.data.db.database.dao.PokemonDetailsDao
import com.example.androidtemplateapp.data.db.ws.api.PokemonApi
import com.example.androidtemplateapp.data.repository.PokemonDetailsRepository
import com.example.androidtemplateapp.entity.PokemonDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class PokemonDetailsRepositoryImpl(
    private val pokemonApi: PokemonApi,
    private val pokemonDetailsDao: PokemonDetailsDao,
) : PokemonDetailsRepository {

    override suspend fun getPokemonDetails(pokemonID: Int): Flow<Result<PokemonDetails>> = flow {
        emit(getLocalPokemonDetails(pokemonID)?.let { localDetails ->
            Result.Success(localDetails)
        } ?: run {
            with(pokemonApi.getPokemon(pokemonID).execute()) {
                return@with body()?.map()?.let { remoteDetails ->
                    savePokemonDetails(remoteDetails)
                    Result.Success(data = remoteDetails)
                } ?: run {
                    Result.Error(ErrorHandler.processError(this))
                }
            }
        })
    }
        .flowOn(Dispatchers.IO)
        .catch { error ->
            emit(Result.Error(AppError(type = AppErrorType.Unknown, cause = error)))
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
