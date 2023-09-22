package com.example.androidbasetemplate.data.db.ws.model.response

import com.example.androidbasetemplate.entity.Pokemon
import com.google.gson.annotations.SerializedName

class PokemonResponse(
    @SerializedName("name")
    var name: String,
    @SerializedName("url")
    var url: String,
) {
    fun map(): Pokemon {
        return Pokemon(
            name = name,
            url = url,
        )
    }
}
