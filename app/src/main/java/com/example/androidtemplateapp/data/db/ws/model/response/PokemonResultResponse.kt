package com.example.androidtemplateapp.data.db.ws.model.response

import com.example.androidtemplateapp.entity.PokemonResult
import com.google.gson.annotations.SerializedName

class PokemonResultResponse(
    @SerializedName("count")
    var count: Int,
    @SerializedName("next")
    var next: String,
    @SerializedName("previous")
    var previous: String?,
    @SerializedName("results")
    var results: List<PokemonResponse>,
) {
    fun map(): PokemonResult {
        return PokemonResult(
            count = count,
            next = next,
            previous = previous,
            results = results.map { pokemonResponse -> pokemonResponse.map() },
        )
    }
}
