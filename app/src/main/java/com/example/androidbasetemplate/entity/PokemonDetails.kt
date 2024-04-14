package com.example.androidbasetemplate.entity

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class PokemonDetails(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "order")
    var order: Int,
    @ColumnInfo(name = "baseExperience")
    var baseExperience: Int,
    @ColumnInfo(name = "height")
    var height: Int,
    @ColumnInfo(name = "weight")
    var weight: Int,
    @ColumnInfo(name = "imageURL")
    var imageURL: String,
    var isDefault: Boolean,
    var locationAreaEncounters: String,
    var species: Species,
    var sprites: Sprites,
    var abilities: List<Ability>,
    var moves: List<Move>,
    var forms: List<Form>,
    var stats: List<Stat>,
    var types: List<TypeX>,
) {
    constructor(
        id: Int,
        name: String,
        order: Int,
        baseExperience: Int,
        height: Int,
        weight: Int,
        imageURL: String,
    ) : this(
        id = id,
        name = name,
        order = order,
        baseExperience = baseExperience,
        height = height,
        weight = weight,
        imageURL = imageURL,
        isDefault = false,
        locationAreaEncounters = "",
        species = Species(),
        sprites = Sprites(),
        abilities = listOf(),
        moves = listOf(),
        forms = listOf(),
        stats = listOf(),
        types = listOf(),
    )
}
