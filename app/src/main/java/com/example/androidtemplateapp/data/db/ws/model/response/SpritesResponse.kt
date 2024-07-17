package com.example.androidtemplateapp.data.db.ws.model.response

import com.example.androidtemplateapp.entity.Sprites
import com.google.gson.annotations.SerializedName

data class SpritesResponse(
    @SerializedName("back_default")
    val backDefault: String?,
    @SerializedName("back_female")
    val backFemale: Any?,
    @SerializedName("back_shiny")
    val backShiny: String?,
    @SerializedName("back_shiny_female")
    val backShinyFemale: Any?,
    @SerializedName("front_default")
    val frontDefault: String?,
    @SerializedName("front_female")
    val frontFemale: Any?,
    @SerializedName("front_shiny")
    val frontShiny: String?,
    @SerializedName("front_shiny_female")
    val frontShinyFemale: Any?,
) {
    fun map() = Sprites(
        backDefault,
        backFemale,
        backShiny,
        backShinyFemale,
        frontDefault,
        frontFemale,
        frontShiny,
        frontShinyFemale
    )
}
