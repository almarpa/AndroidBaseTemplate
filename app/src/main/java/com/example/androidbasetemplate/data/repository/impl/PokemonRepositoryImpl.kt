package com.example.androidbasetemplate.data.repository.impl

import com.example.androidbasetemplate.data.db.dao.PokemonDao
import com.example.androidbasetemplate.data.db.ws.api.PokemonApi
import com.example.androidbasetemplate.data.repository.PokemonRepository
import com.example.androidbasetemplate.entity.Pokemon
import com.example.androidbasetemplate.entity.PokemonDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl.Companion.toHttpUrl

class PokemonRepositoryImpl(
    private val pokemonApi: PokemonApi,
    private val pokemonDao: PokemonDao,
) : PokemonRepository {

    override suspend fun getPokemons() = flow {
        emit(
            with(
                pokemonApi.getPokemons().execute(),
            ) {
                body()?.map()?.results?.onEach { pokemon ->
                    pokemon.url = getPokemon(pokemon.url).sprites
                }?.also { savePokemonList(it) } ?: listOf()
            },
        )
    }

    private suspend fun savePokemonList(pokemonList: List<Pokemon>) {
        withContext(Dispatchers.IO) {
            pokemonDao.insertAll(pokemonList)
        }
    }

    private suspend fun getLocalPokemonList(): List<Pokemon> {
        return withContext(Dispatchers.IO) {
            pokemonDao.getAll()
        }
    }

    override suspend fun getPokemon(pokemonUrl: String): PokemonDetail {
        return withContext(Dispatchers.IO) {
            try {
                with(
                    pokemonApi.getPokemon(with(pokemonUrl.toHttpUrl().pathSegments) { get(size - 2) }.toInt())
                        .execute(),
                ) {
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
