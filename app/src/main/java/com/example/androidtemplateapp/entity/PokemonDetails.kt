package com.example.androidtemplateapp.entity

import com.example.androidtemplateapp.data.db.database.entity.PokemonDetailsEntity

data class PokemonDetails(
    var id: Int,
    var name: String,
    var order: Int,
    var baseExperience: Int,
    var height: Int,
    var weight: Int,
    var imageURL: String,
    var stats: List<Stat>,
    var types: List<TypeX>,
    var isDefault: Boolean,
    var locationAreaEncounters: String,
    var species: Species,
    var sprites: Sprites,
    var abilities: List<Ability>,
    var moves: List<Move>,
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
        isDefault = false,
        locationAreaEncounters = "",
        species = Species(),
        sprites = Sprites(),
        abilities = listOf(),
        moves = listOf(),
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
            isDefault,
            locationAreaEncounters,
            species,
            sprites,
            abilities,
            moves,
            forms
        )
}
