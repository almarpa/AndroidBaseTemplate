package com.example.androidbasetemplate.data.db.ws.model.response

import com.example.androidbasetemplate.entity.PokemonDetail
import com.google.gson.annotations.SerializedName

class PokemonDetailResponse(
    @SerializedName("id")
    var id: Int,
    @SerializedName("order")
    var order: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("base_experience")
    var baseExperience: Int,
    @SerializedName("height")
    var height: Int,
    @SerializedName("weight")
    var weight: Int,
    @SerializedName("types")
    var types: List<PokemonTypeResponse>,
    @SerializedName("sprites")
    var sprites: String,
) {
    fun map(): PokemonDetail {
        return PokemonDetail(
            id,
            order,
            name,
            baseExperience,
            height,
            weight,
            types.map { typeResponse -> typeResponse.map() },
            sprites,
        )
    }
}
