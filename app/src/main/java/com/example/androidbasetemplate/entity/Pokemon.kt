package com.example.androidbasetemplate.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
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

    var dominantColor: Int? = null,
) : Parcelable {

    fun toJSONString(): String = Gson().toJson(apply { url = getEncodedUrl(url) })

    private fun getEncodedUrl(url: String) =
        if (urlIsEncodedYet(url)) {
            url
        } else {
            URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
        }

    private fun urlIsEncodedYet(url: String) = url.contains("%2")
}