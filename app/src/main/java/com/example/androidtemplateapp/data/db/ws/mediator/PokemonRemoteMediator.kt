package com.example.androidtemplateapp.data.db.ws.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.androidtemplateapp.data.db.database.PokemonDataBase
import com.example.androidtemplateapp.data.db.database.entity.PokemonEntity
import com.example.androidtemplateapp.data.db.ws.api.PokemonApi
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val pokemonDatabase: PokemonDataBase,
    private val pokemonApi: PokemonApi,
) : RemoteMediator<Int, PokemonEntity>() {

    companion object {
        private const val POKEMON_RESULTS_LIMIT = 1302
        private const val POKEMON_RESULTS_OFFSET = 0
        private const val REFRESHING_TIME_FROM_REMOTE = 168L
    }

    /**
     * Re-fetch from the network every week on app init.
     */
    override suspend fun initialize(): InitializeAction {
        val cacheTimeout =
            TimeUnit.MILLISECONDS.convert(REFRESHING_TIME_FROM_REMOTE, TimeUnit.HOURS)
        return if (System.currentTimeMillis() - pokemonDatabase.getDatabaseCreationDate() <= cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    /**
     * Responsible for updating the backing dataset and invalidating the PagingSource
     */
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>,
    ): MediatorResult {
        return try {
            when (loadType) {
                LoadType.REFRESH -> {
                    // Request all data from network at first time to allow local search
                    with(pokemonApi.getPokemons(POKEMON_RESULTS_LIMIT, POKEMON_RESULTS_OFFSET)) {
                        pokemonDatabase.withTransaction {
                            pokemonDatabase.pokemonDao().insertAll(
                                results.map { pokemonResponse -> pokemonResponse.map().asEntity() }
                            )
                        }
                        MediatorResult.Success(endOfPaginationReached = results.isEmpty())
                    }
                }

                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
            }
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}