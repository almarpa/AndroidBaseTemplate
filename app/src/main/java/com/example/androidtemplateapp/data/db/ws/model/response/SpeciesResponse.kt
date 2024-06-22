package com.example.androidtemplateapp.data.db.ws.model.response

import com.example.androidtemplateapp.entity.Species
import com.google.gson.annotations.SerializedName

data class SpeciesResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
) {
    fun map() = Species(
        name = name,
        url = url,
    )
}
