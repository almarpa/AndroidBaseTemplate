package com.example.androidbasetemplate.data.db.ws.model.response

import com.example.androidbasetemplate.entity.PokemonDetails
import com.google.gson.annotations.SerializedName

data class PokemonDetailsResponse(
    @SerializedName("abilities")
    val abilities: List<AbilityResponse>,
    @SerializedName("base_experience")
    val baseExperience: Int,
    @SerializedName("forms")
    val forms: List<FormResponse>,
    @SerializedName("height")
    val height: Int,
    @SerializedName("held_items")
    val heldItems: List<Any>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_default")
    val isDefault: Boolean,
    @SerializedName("location_area_encounters")
    val locationAreaEncounters: String,
    @SerializedName("moves")
    val moves: List<MoveResponse>,
    @SerializedName("name")
    val name: String,
    @SerializedName("order")
    val order: Int,
    @SerializedName("species")
    val species: SpeciesResponse,
    @SerializedName("sprites")
    val sprites: SpritesResponse,
    @SerializedName("stats")
    val stats: List<StatResponse>,
    @SerializedName("types")
    val types: List<TypeResponseX>,
    @SerializedName("weight")
    val weight: Int,
) {
    fun map(): PokemonDetails {
        return PokemonDetails(
            id = id,
            name = name,
            order = order,
            baseExperience = baseExperience,
            height = height,
            weight = weight,
            imageURL = getPokemonImageURL(),
            stats = stats.map { inner -> inner.map() },
            types = types.map { inner -> inner.map() },
            isDefault = false,
            locationAreaEncounters = "",
            species = species.map(),
            sprites = sprites.map(),
            abilities = listOf(),
            moves = moves.map { inner -> inner.map() },
            forms = forms.map { inner -> inner.map() },
        )
    }

    private fun getPokemonImageURL() =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png"
}
