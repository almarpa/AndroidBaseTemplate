package com.example.androidbasetemplate.data.db.ws.model.response

import com.example.androidbasetemplate.entity.TypeXX
import com.example.androidbasetemplate.entity.enums.PokemonTypeEnum
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
