package com.example.androidtemplateapp.data.repository.impl

import com.example.androidtemplateapp.common.errorhandler.entity.AppErrorType
import com.example.androidtemplateapp.common.utils.Resource
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

    override suspend fun getPokemonDetails(pokemonID: Int): Flow<Resource<PokemonDetails>> = flow {
        emit(Resource.Loading())
        emit(getLocalPokemonDetails(pokemonID)?.let { localDetails ->
            Resource.Success(localDetails)
        } ?: run {
            with(pokemonApi.getPokemon(pokemonID).execute()) {
                return@with body()?.map()?.let { remoteDetails ->
                    savePokemonDetails(remoteDetails)
                    Resource.Success(data = remoteDetails)
                } ?: run {
                    Resource.Error(AppErrorType.MalformedResponse)
                }
            }
        })
    }
        .flowOn(Dispatchers.IO)
        .catch { error ->
            emit(Resource.Error(AppErrorType.Api.Server))
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
