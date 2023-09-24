package com.example.androidbasetemplate.entity

data class PokemonResult(
    var count: Int?,
    var next: String?,
    var previous: String?,
    var results: List<Pokemon>,
)
