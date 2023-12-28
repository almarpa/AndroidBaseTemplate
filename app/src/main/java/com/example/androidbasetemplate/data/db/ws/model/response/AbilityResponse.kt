package com.example.androidbasetemplate.data.db.ws.model.response

import com.google.gson.annotations.SerializedName

data class AbilityResponse(
    @SerializedName("ability")
    val ability: AbilityResponseX,
    @SerializedName("is_hidden")
    val isHidden: Boolean,
    @SerializedName("slot")
    val slot: Int,
)
