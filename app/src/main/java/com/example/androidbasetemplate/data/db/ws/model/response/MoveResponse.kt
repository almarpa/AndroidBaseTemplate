package com.example.androidbasetemplate.data.db.ws.model.response

import com.example.androidbasetemplate.entity.Move
import com.google.gson.annotations.SerializedName

data class MoveResponse(
    @SerializedName("move")
    val moveResponseX: MoveResponseX,
) {
    fun map() = Move(
        moveResponseX.map(),
    )
}
