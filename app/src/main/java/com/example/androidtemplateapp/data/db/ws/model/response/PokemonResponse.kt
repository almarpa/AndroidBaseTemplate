package com.example.androidtemplateapp.data.db.ws.model.response

import com.example.androidtemplateapp.entity.Pokemon
import com.google.gson.annotations.SerializedName
import okhttp3.HttpUrl.Companion.toHttpUrl

class PokemonResponse(
    @SerializedName("name")
    var name: String,
    @SerializedName("url")
    var url: String,
) {
    fun map(): Pokemon {
        return Pokemon(
            id = getPokemonID(),
            name = name,
            url = getPokemonImageURL(),
        )
    }

    private fun getPokemonID() = with(url.toHttpUrl().pathSegments) { get(size - 2) }.toInt()
    
    private fun getPokemonImageURL() =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${getPokemonID()}.png"

}
