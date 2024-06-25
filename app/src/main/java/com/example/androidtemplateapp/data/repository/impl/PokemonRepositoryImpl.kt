package com.example.androidtemplateapp.data.repository.impl

import com.example.androidtemplateapp.data.db.database.dao.PokemonDao
import com.example.androidtemplateapp.data.db.ws.api.PokemonApi
import com.example.androidtemplateapp.data.repository.PokemonRepository
import com.example.androidtemplateapp.entity.Pokemon
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
                        remotePokemonList.also { savePokemons(it) }
                    } ?: emptyList()
                }
            }
        )
    }
        .flowOn(Dispatchers.IO)
        .catch { error ->
            throw Exception(error)
        }

    override suspend fun getTeamMembers() = flow {
        emit(getLocalTeamMembers())
    }

    override suspend fun addPokemonToTeam(pokemon: Pokemon) {
        withContext(Dispatchers.IO) {
            pokemonDao.update(pokemon.asEntity())
        }
    }

    override suspend fun searchPokemonsByName(name: String) = flow {
        emit(searchLocalPokemonsByName(name))
    }

    override suspend fun createTeamMember(pokemon: Pokemon) {
        withContext(Dispatchers.IO) {
            pokemonDao.insert(pokemon.asEntity())
        }
    }

    private suspend fun savePokemons(pokemonList: List<Pokemon>) {
        withContext(Dispatchers.IO) {
            pokemonDao.insertAll(pokemonList.map { it.asEntity() })
        }
    }

    private suspend fun getLocalPokemonList(): List<Pokemon> {
        return withContext(Dispatchers.IO) {
            pokemonDao.getAll().map { pokemonEntity -> pokemonEntity.asDomain() }
        }
    }

    private suspend fun getLocalTeamMembers(): List<Pokemon> {
        return withContext(Dispatchers.IO) {
            pokemonDao.getAllTeamMembers().map { it.asDomain() }
        }
    }

    private suspend fun searchLocalPokemonsByName(name: String): List<Pokemon> {
        return withContext(Dispatchers.IO) {
            pokemonDao.searchPokemonByName(name.lowercase()).map { it.asDomain() }
        }
    }
}
