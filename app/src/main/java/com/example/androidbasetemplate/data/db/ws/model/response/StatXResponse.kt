package com.example.androidbasetemplate.data.db.ws.model.response

import com.example.androidbasetemplate.entity.StatX
import com.example.androidbasetemplate.entity.enums.StatNameEnum
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
