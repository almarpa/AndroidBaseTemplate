package com.example.androidtemplateapp.data.db.ws.model.response

import com.example.androidtemplateapp.entity.StatX
import com.example.androidtemplateapp.entity.enums.StatNameEnum
import com.google.gson.annotations.SerializedName

data class StatXResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
) {
    fun map() = StatX(
        StatNameEnum.from(name),
        url
    )
}
