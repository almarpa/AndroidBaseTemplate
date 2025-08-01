package com.example.androidtemplateapp.entity

import com.example.androidtemplateapp.data.db.database.entity.PokemonDetailsEntity

data class PokemonDetails(
    val id: Int,
    val name: String,
    val order: Int,
    val baseExperience: Int,
    val height: Int,
    val weight: Int,
    val imageURL: String,
    val stats: List<Stat>,
    val types: List<TypeX>,
    val moves: List<Move>,
    val isDefault: Boolean,
    val locationAreaEncounters: String,
    val species: Species,
    val sprites: Sprites,
    val abilities: List<Ability>,
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

    fun asEntity(): PokemonDetailsEntity =
        PokemonDetailsEntity(
            id,
            name,
            order,
            baseExperience,
            height,
            weight,
            imageURL,
            stats,
            types,
            moves,
            isDefault,
            locationAreaEncounters,
            species,
            sprites,
            abilities,
            forms
        )
}
