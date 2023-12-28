package com.example.androidbasetemplate.data.db.ws.model.response

import com.example.androidbasetemplate.entity.Stat
import com.google.gson.annotations.SerializedName

data class StatResponse(
    @SerializedName("base_stat")
    val baseStat: Int,
    @SerializedName("effort")
    val effort: Int,
    @SerializedName("stat")
    val stat: StatResponseX,
) {
    fun map() = Stat(
        baseStat,
        effort,
        stat.map(),
    )
}
