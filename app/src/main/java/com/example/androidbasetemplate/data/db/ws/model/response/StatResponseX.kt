package com.example.androidbasetemplate.data.db.ws.model.response

import com.example.androidbasetemplate.entity.StatX
import com.google.gson.annotations.SerializedName

data class StatResponseX(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
) {
    fun map() = StatX(
        name,
        url
    )
}
