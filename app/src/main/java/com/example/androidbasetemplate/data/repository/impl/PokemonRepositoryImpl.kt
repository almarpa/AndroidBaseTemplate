package com.example.androidbasetemplate.data.repository.impl

import com.example.androidbasetemplate.data.db.dao.PokemonDao
import com.example.androidbasetemplate.data.db.ws.api.PokemonApi
import com.example.androidbasetemplate.data.repository.PokemonRepository
import com.example.androidbasetemplate.entity.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.lang.Exception

class PokemonRepositoryImpl(
    private val pokemonApi: PokemonApi,
    private val pokemonDao: PokemonDao,
) : PokemonRepository {

    override suspend fun getPokemons() = flow {
            emit(pokemonDao.getAll().ifEmpty {
                with(
                    pokemonApi.getPokemons().execute()
                ) {
                    body()?.map()?.results?.also { savePokemonList(it) } ?: listOf()
                }
            })
    }

    private suspend fun savePokemonList(pokemonList: List<Pokemon>) {
        withContext(Dispatchers.IO) {
            pokemonDao.insertAll(pokemonList)
        }
    }

    private suspend fun getLocalPokemonList(): List<Pokemon>? {
        return withContext(Dispatchers.IO) {
            pokemonDao.getAll()
        }
    }

    override suspend fun getPokemon(pokemonId: Int): Pokemon {
        return withContext(Dispatchers.IO) {
            try {
                with(
                    pokemonApi.getPokemon(pokemonId).execute(),
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
