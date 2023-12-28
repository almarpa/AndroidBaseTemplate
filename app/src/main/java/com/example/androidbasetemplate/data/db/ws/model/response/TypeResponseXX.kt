package com.example.androidbasetemplate.data.db.ws.model.response

import com.example.androidbasetemplate.entity.TypeXX
import com.google.gson.annotations.SerializedName

data class TypeResponseXX(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
) {
    fun map() = TypeXX(
        name,
        url
    )
}
