package com.example.androidtemplateapp.data.repository.impl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.androidtemplateapp.data.db.database.PokemonDataBase
import com.example.androidtemplateapp.data.db.database.dao.PokemonDao
import com.example.androidtemplateapp.data.db.ws.api.PokemonApi
import com.example.androidtemplateapp.data.db.ws.mediator.PokemonRemoteMediator
import com.example.androidtemplateapp.data.repository.PokemonRepository
import com.example.androidtemplateapp.entity.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PokemonRepositoryImpl(
    private val pokemonApi: PokemonApi,
    private val pokemonDao: PokemonDao,
    private val pokemonDatabase: PokemonDataBase,
) : PokemonRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPokemons(pageSize: Int): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize
            ),
            remoteMediator = PokemonRemoteMediator(
                pokemonDatabase = pokemonDatabase,
                pokemonApi = pokemonApi
            ),
            pagingSourceFactory = {
                pokemonDatabase.pokemonDao().getAllPaged()
            }
        ).flow.map { pagingData -> pagingData.map { it.asDomain() } }
    }

    override fun getTeamMembers(): Flow<List<Pokemon>> =
        pokemonDao.getAllTeamMembers().map { pokemonList ->
            pokemonList.map { it.asDomain() }
        }.flowOn(Dispatchers.IO)

    override fun searchPokemonByName(name: String): Flow<List<Pokemon>> =
        pokemonDao.searchPokemonByName(name.lowercase()).map { pokemonList ->
            pokemonList.map { it.asDomain() }
        }.flowOn(Dispatchers.IO)

    override suspend fun addPokemonToTeam(pokemon: Pokemon) {
        withContext(Dispatchers.IO) {
            pokemonDao.update(pokemon.asEntity())
        }
    }

    override suspend fun createPokemonMember(pokemon: Pokemon) {
        withContext(Dispatchers.IO) {
            pokemonDao.insert(pokemon.asEntity())
        }
    }
}
