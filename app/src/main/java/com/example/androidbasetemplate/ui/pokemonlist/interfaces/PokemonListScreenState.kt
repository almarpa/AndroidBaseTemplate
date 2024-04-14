package com.example.androidbasetemplate.ui.pokemonlist.interfaces

sealed interface PokemonListScreenState {
    object List : PokemonListScreenState
    data class Details(val item: Int) : PokemonListScreenState
}