package com.example.androidbasetemplate.ui.pokemonlist

import androidx.compose.ui.graphics.Color

sealed interface PokemonListScreenState {
    object List : PokemonListScreenState
    data class Details(val pokemonDetails: Pair<Int, Color>) : PokemonListScreenState
}