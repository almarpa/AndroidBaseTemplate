package com.example.androidbasetemplate.data.repository.impl

import com.example.androidbasetemplate.data.db.dao.PokemonDao
import com.example.androidbasetemplate.data.db.ws.api.PokemonApi
import com.example.androidbasetemplate.data.repository.PokemonRepository
import com.example.androidbasetemplate.entity.Pokemon
import com.example.androidbasetemplate.entity.PokemonDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl.Companion.toHttpUrl

class PokemonRepositoryImpl(
    private val pokemonApi: PokemonApi,
    private val pokemonDao: PokemonDao,
) : PokemonRepository {

    override suspend fun getPokemons() = flow {
        with(
            pokemonApi.getPokemons().execute(),
        ) {
            emit(
                body()?.map()?.results?.onEach { pokemon ->
                    pokemon.url =
                        getPokemon(
                            with(pokemon.url.toHttpUrl().pathSegments) { get(size - 2) }.toInt(),
                        ).getDefaultImage()
                    savePokemon(pokemon)
                } ?: getLocalPokemonList(),
            )
        }
    }
        .flowOn(Dispatchers.IO)
        .catch { error ->
            throw Exception(error)
        }

    private suspend fun savePokemon(pokemon: Pokemon) {
        withContext(Dispatchers.IO) {
            pokemonDao.insert(pokemon)
        }
    }

    private suspend fun getLocalPokemonList(): List<Pokemon> {
        return withContext(Dispatchers.IO) {
            pokemonDao.getAll()
        }
    }

    override suspend fun getPokemon(pokemonID: Int): PokemonDetails {
        return withContext(Dispatchers.IO) {
            try {
                with(pokemonApi.getPokemon(pokemonID).execute()) {
                    body()?.let { body ->
                        return@let body.map()
                    } ?: run {
                        throw Exception()
                    }
                }
            } catch (e: Throwable) {
                throw Exception(e)
            }
        }
    }
}
