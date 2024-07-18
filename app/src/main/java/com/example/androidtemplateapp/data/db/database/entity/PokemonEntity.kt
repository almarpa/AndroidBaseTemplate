package com.example.androidtemplateapp.data.db.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidtemplateapp.entity.Pokemon
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Entity(tableName = "pokemon")
@Parcelize
@Serializable
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
) : Parcelable {

    fun asDomain(): Pokemon = Pokemon(id, url, name, dominantColor, isTeamMember)
}