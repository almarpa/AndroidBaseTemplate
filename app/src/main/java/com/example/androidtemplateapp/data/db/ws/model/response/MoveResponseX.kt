package com.example.androidtemplateapp.data.db.ws.model.response

import com.example.androidtemplateapp.entity.MoveX
import com.google.gson.annotations.SerializedName

data class MoveResponseX(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
) {
    fun map() = MoveX(
        name,
        url
    )
}
