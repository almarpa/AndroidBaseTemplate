package com.example.androidbasetemplate.entity

import android.os.Parcelable
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Entity(tableName = "pokemon")
@Parcelize
@Serializable
data class Pokemon(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "url")
    var url: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "dominantColor")
    var dominantColor: Int? = null,
    @ColumnInfo(name = "isTeamMember")
    var isTeamMember: Boolean = false,
) : Parcelable {

    @Composable
    fun getDominantColorOrDefault()  = dominantColor?.let { Color(it) } ?: MaterialTheme.colorScheme.primary

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