package com.example.androidtemplateapp.entity

import android.os.Parcelable
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.androidtemplateapp.data.db.database.entity.PokemonEntity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Parcelize
@Serializable
data class Pokemon(
    var id: Int,
    var url: String,
    var name: String,
    var dominantColor: Int? = null,
    var isTeamMember: Boolean,
) : Parcelable {

    constructor(
        url: String,
        name: String,
        dominantColor: Int,
    ) : this(
        id = 0,
        url = url,
        name = name,
        dominantColor = dominantColor,
        isTeamMember = true
    )

    fun asEntity(): PokemonEntity {
        return PokemonEntity(
            id = id,
            url = url,
            name = name,
            dominantColor = dominantColor,
            isTeamMember = isTeamMember
        )
    }

    @Composable
    fun getDominantColorOrDefault() =
        dominantColor?.let { Color(it) } ?: MaterialTheme.colorScheme.primary

    companion object {
        fun String.getPokemonFromJson(): Pokemon =
            GsonBuilder().create().fromJson(this, Pokemon::class.java)
    }

    fun pokemonToJSONString(): String = Gson().toJson(apply { url = getEncodedUrl(url) })

    private fun getEncodedUrl(url: String) =
        if (urlIsEncodedYet(url)) {
            url
        } else {
            URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
        }

    private fun urlIsEncodedYet(url: String) = url.contains("%2")
}