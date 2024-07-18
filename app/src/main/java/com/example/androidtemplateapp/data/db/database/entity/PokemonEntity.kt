package com.example.androidtemplateapp.data.db.database.entity

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
    @ColumnInfo(name = "dominantColor")
    var dominantColor: Int? = null,
    @ColumnInfo(name = "isTeamMember")
    var isTeamMember: Boolean = false,
    @ColumnInfo(name = "createdAt")
    var createdAt: Long = 0L,
) {

    fun asDomain(): Pokemon = Pokemon(
        id,
        url,
        name,
        dominantColor,
        isTeamMember
    )
}