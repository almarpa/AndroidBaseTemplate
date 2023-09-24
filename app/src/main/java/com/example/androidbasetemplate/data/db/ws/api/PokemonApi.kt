package com.example.androidbasetemplate.data.db.ws.api

import com.example.androidbasetemplate.data.db.ws.model.response.PokemonResponse
import com.example.androidbasetemplate.data.db.ws.model.response.PokemonResultResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("api/v2/pokemon")
    fun getPokemons(
        @Query("limit") limit: Int? = 50,
        @Query("offset") offset: Int? = 0,
    ): Call<PokemonResultResponse>

    @GET("api/v2/pokemon/{pokemonID}")
    fun getPokemon(@Path("pokemonID") pokemonId: Int): Call<PokemonResponse>
}
