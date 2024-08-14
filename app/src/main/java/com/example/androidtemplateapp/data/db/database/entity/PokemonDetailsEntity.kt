package com.example.androidtemplateapp.data.db.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.androidtemplateapp.entity.*

@Entity(tableName = "pokemonDetails")
data class PokemonDetailsEntity(
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
    @ColumnInfo(name = "stats")
    var stats: List<Stat>,
    @ColumnInfo(name = "types")
    var types: List<TypeX>,
    @ColumnInfo(name = "moves")
    var moves: List<Move>,
    @Ignore
    var isDefault: Boolean,
    @Ignore
    var locationAreaEncounters: String,
    @Ignore
    var species: Species,
    @Ignore
    var sprites: Sprites,
    @Ignore
    var abilities: List<Ability>,
    @Ignore
    var forms: List<Form>,
) {
    constructor(
        id: Int,
        name: String,
        order: Int,
        baseExperience: Int,
        height: Int,
        weight: Int,
        imageURL: String,
        stats: List<Stat>,
        types: List<TypeX>,
        moves: List<Move>,
    ) : this(
        id = id,
        name = name,
        order = order,
        baseExperience = baseExperience,
        height = height,
        weight = weight,
        imageURL = imageURL,
        stats = stats,
        types = types,
        moves = moves,
        isDefault = false,
        locationAreaEncounters = "",
        species = Species(),
        sprites = Sprites(),
        abilities = listOf(),
        forms = listOf(),
    )

    fun asDomain(): PokemonDetails = PokemonDetails(
        id = id,
        name = name,
        order = order,
        baseExperience = baseExperience,
        height = height,
        weight = weight,
        imageURL = imageURL,
        stats = stats,
        types = types,
        moves = moves,
        isDefault = isDefault,
        locationAreaEncounters = locationAreaEncounters,
        species = species,
        sprites = sprites,
        abilities = abilities,
        forms = forms
    )
}
