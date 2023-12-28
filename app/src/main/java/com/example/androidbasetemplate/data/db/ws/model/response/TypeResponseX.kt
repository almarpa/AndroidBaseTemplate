package com.example.androidbasetemplate.data.db.ws.model.response

import com.example.androidbasetemplate.entity.TypeX
import com.google.gson.annotations.SerializedName

data class TypeResponseX(
    @SerializedName("slot")
    val slot: Int,
    @SerializedName("type")
    val type: TypeResponseXX,
) {
    fun map() = TypeX(
        slot,
        type.map()
    )
}
