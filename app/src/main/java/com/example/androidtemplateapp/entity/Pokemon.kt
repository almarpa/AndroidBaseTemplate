package com.example.androidtemplateapp.entity

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.androidtemplateapp.data.db.database.entity.PokemonEntity
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Pokemon(
    val id: Int,
    val url: String,
    val name: String,
    val createdAt: Long = System.currentTimeMillis(),
    var dominantColor: Int = Color.Transparent.toArgb(),
    var isTeamMember: Boolean = false,
) : Parcelable {

    constructor(
        url: String,
        name: String,
        dominantColor: Int,
    ) : this(
        id = 0,
        url = url,
        name = name,
        createdAt = System.currentTimeMillis(),
        dominantColor = dominantColor,
        isTeamMember = true,
    )

    fun asEntity(): PokemonEntity =
        PokemonEntity(
            id = id,
            url = url,
            name = name,
            createdAt = createdAt,
            dominantColor = dominantColor,
            isTeamMember = isTeamMember,
        )

    fun getDominantColor() =
        if (dominantColor == Color.Transparent.toArgb()) {
            null
        } else {
            Color(dominantColor)
        }
}