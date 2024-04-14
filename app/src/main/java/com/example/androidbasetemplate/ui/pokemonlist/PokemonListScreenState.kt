package com.example.androidbasetemplate.ui.pokemonlist

sealed interface PokemonListScreenState {
    object List : PokemonListScreenState
    data class Details(val item: Int) : PokemonListScreenState
}