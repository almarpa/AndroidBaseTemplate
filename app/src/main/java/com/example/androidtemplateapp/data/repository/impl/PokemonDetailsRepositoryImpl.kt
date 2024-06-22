package com.example.androidtemplateapp.data.repository.impl

import com.example.androidtemplateapp.data.db.database.dao.PokemonDetailsDao
import com.example.androidtemplateapp.data.db.ws.api.PokemonApi
import com.example.androidtemplateapp.data.repository.PokemonDetailsRepository
import com.example.androidtemplateapp.entity.PokemonDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class PokemonDetailsRepositoryImpl(
    private val pokemonApi: PokemonApi,
    private val pokemonDetailsDao: PokemonDetailsDao,
) : PokemonDetailsRepository {

    override suspend fun getPokemonDetails(pokemonID: Int) = flow {
        emit(
            getLocalPokemonDetails(pokemonID) ?: run {
                with(
                    pokemonApi.getPokemon(pokemonID).execute(),
                ) {
                    return@with body()?.map()?.let { pokemonDetails ->
                        pokemonDetails.also { savePokemonDetails(it) }
                    } ?: run {
                        throw Exception()
                    }
                }
            }
        )
    }
        .flowOn(Dispatchers.IO)
        .catch { error ->
            throw Exception(error)
        }

    private suspend fun getLocalPokemonDetails(pokemonID: Int): PokemonDetails? {
        return withContext(Dispatchers.IO) {
            pokemonDetailsDao.get(pokemonID.toString())
        }
    }

    private suspend fun savePokemonDetails(pokemonDetails: PokemonDetails) {
        withContext(Dispatchers.IO) {
            pokemonDetailsDao.insert(pokemonDetails)
        }
    }
}
