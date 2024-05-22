package com.example.androidbasetemplate.ui.common.mocks

import com.example.androidbasetemplate.domain.mock.PokemonUseCaseImplMock
import com.example.androidbasetemplate.domain.mock.UserDataUseCaseImplMock
import com.example.androidbasetemplate.entity.Pokemon
import com.example.androidbasetemplate.ui.pokemonlist.PokemonListViewModel
import com.example.androidbasetemplate.ui.settings.SettingsViewModel


/****************************
 *******   VIEWMODEL  *******
 ***************************/
fun getPokemonListViewModelMock() = PokemonListViewModel(PokemonUseCaseImplMock())
fun getSettingsViewModelMock() = SettingsViewModel(UserDataUseCaseImplMock())

/****************************
 *********   DATA   *********
 ***************************/
fun getPokemonListMock() =
    listOf(
        Pokemon(
            id = 1,
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
            name = "Pokemon 1",
        ),
        Pokemon(
            id = 2,
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/2.png",
            name = "Pokemon 2",
        ),
        Pokemon(
            id = 3,
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/3.png",
            name = "Pokemon 3",
        ),
        Pokemon(
            id = 4,
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/2.png",
            name = "Pokemon 4",
        ),
        Pokemon(
            id = 5,
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/3.png",
            name = "Pokemon 5",
        ),
        Pokemon(
            id = 6,
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/3.png",
            name = "Pokemon 6",
        )
    )