package com.example.androidbasetemplate.data.db.ws.model.response

import com.example.androidbasetemplate.entity.Type
import com.google.gson.annotations.SerializedName

class TypeResponse(
    @SerializedName("name")
    var name: String,
    @SerializedName("url")
    var url: String,
) {
    fun map(): Type {
        return Type(
            name = name,
            url = url,
        )
    }
}
