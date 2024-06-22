package com.example.androidtemplateapp.data.db.ws.model.response

import com.example.androidtemplateapp.entity.TypeXX
import com.example.androidtemplateapp.entity.enums.PokemonTypeEnum
import com.google.gson.annotations.SerializedName

data class TypeXXResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
) {
    fun map() = TypeXX(
        PokemonTypeEnum.from(name),
        url
    )
}
