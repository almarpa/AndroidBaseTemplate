package com.example.androidtemplateapp.data.db.ws.api

import com.example.androidtemplateapp.data.db.ws.model.response.PokemonDetailsResponse
import com.example.androidtemplateapp.data.db.ws.model.response.PokemonResultResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("api/v2/pokemon")
    suspend fun getPokemons(
        @Query("limit") limit: Int? = 20,
        @Query("offset") offset: Int? = 0,
    ): PokemonResultResponse

    @GET("api/v2/pokemon/{pokemonID}")
    fun getPokemon(@Path("pokemonID") pokemonId: Int): Call<PokemonDetailsResponse>
}
