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

class PokemonRepositoryImpl(
    private val pokemonApi: PokemonApi,
    private val pokemonDao: PokemonDao,
) : PokemonRepository {

    override suspend fun getPokemons() = flow {
        emit(
            getLocalPokemonList().ifEmpty {
                with(
                    pokemonApi.getPokemons().execute(),
                ) {
                    body()?.map()?.results?.let { remotePokemonList ->
                        remotePokemonList.onEach { pokemon -> savePokemon(pokemon) }
                    } ?: emptyList()
                }
            }
        )
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
                    return@with body()?.map() ?: run {
                        throw Exception()
                    }
                }
            } catch (e: Throwable) {
                throw Exception(e)
            }
        }
    }
}
