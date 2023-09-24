package com.example.androidbasetemplate.data.db.ws.model.response

import com.example.androidbasetemplate.entity.PokemonType
import com.google.gson.annotations.SerializedName

class PokemonTypeResponse(
    @SerializedName("slot")
    var slot: Int,
    @SerializedName("type")
    var type: TypeResponse,
) {
    fun map(): PokemonType {
        return PokemonType(
            slot,
            type.map(),
        )
    }
}
