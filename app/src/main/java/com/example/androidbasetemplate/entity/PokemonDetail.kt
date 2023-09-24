package com.example.androidbasetemplate.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemonDetail")
data class PokemonDetail(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "order")
    var order: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "baseExperience")
    var baseExperience: Int,
    @ColumnInfo(name = "height")
    var height: Int,
    @ColumnInfo(name = "weight")
    var weight: Int,
    @ColumnInfo(name = "types")
    var types: List<PokemonType>,
    @ColumnInfo(name = "sprites")
    var sprites: String,
)
