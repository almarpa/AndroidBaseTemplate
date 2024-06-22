package com.example.androidtemplateapp.entity

data class PokemonResult(
    var count: Int?,
    var next: String?,
    var previous: String?,
    var results: List<Pokemon>,
)
