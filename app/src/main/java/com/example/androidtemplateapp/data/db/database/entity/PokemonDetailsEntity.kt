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
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "order")
    val order: Int,
    @ColumnInfo(name = "baseExperience")
    val baseExperience: Int,
    @ColumnInfo(name = "height")
    val height: Int,
    @ColumnInfo(name = "weight")
    val weight: Int,
    @ColumnInfo(name = "imageURL")
    val imageURL: String,
    @ColumnInfo(name = "stats")
    val stats: List<Stat>,
    @ColumnInfo(name = "types")
    val types: List<TypeX>,
    @ColumnInfo(name = "moves")
    val moves: List<Move>,
    @Ignore
    val isDefault: Boolean,
    @Ignore
    val locationAreaEncounters: String,
    @Ignore
    val species: Species,
    @Ignore
    val sprites: Sprites,
    @Ignore
    val abilities: List<Ability>,
    @Ignore
    val forms: List<Form>,
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
