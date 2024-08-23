package com.example.androidtemplateapp.data.db.database.entity

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidtemplateapp.entity.Pokemon

@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "url")
    var url: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "createdAt")
    var createdAt: Long = 0L,
    @ColumnInfo(name = "dominantColor")
    var dominantColor: Int = Color.Transparent.toArgb(),
    @ColumnInfo(name = "isTeamMember")
    var isTeamMember: Boolean = false,
) {

    fun asDomain(): Pokemon = Pokemon(
        id = id,
        url = url,
        name = name,
        createdAt = createdAt,
        dominantColor = dominantColor,
        isTeamMember = isTeamMember,
    )
}