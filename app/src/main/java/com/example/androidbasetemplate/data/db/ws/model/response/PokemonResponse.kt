package com.example.androidbasetemplate.data.db.ws.model.response

import com.example.androidbasetemplate.entity.Pokemon
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
            name = name,
            url = getPokemonImageURL(),
        )
    }

    private fun getPokemonImageURL() =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${getPokemonID()}.png"

    private fun getPokemonID() = with(url.toHttpUrl().pathSegments) { get(size - 2) }.toInt()
}
