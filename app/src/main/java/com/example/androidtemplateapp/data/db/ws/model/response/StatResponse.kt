package com.example.androidtemplateapp.data.db.ws.model.response

import com.example.androidtemplateapp.entity.Stat
import com.google.gson.annotations.SerializedName

data class StatResponse(
    @SerializedName("base_stat")
    val baseStat: Int,
    @SerializedName("effort")
    val effort: Int,
    @SerializedName("stat")
    val statX: StatXResponse,
) {
    fun map() = Stat(
        baseStat,
        effort,
        statX.map(),
    )
}
