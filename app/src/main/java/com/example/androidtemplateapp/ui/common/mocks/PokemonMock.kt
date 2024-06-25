package com.example.androidtemplateapp.ui.common.mocks

import androidx.lifecycle.SavedStateHandle
import com.example.androidtemplateapp.domain.mock.PokemonDetailsUseCaseImplMock
import com.example.androidtemplateapp.domain.mock.PokemonUseCaseImplMock
import com.example.androidtemplateapp.domain.mock.UserDataUseCaseImplMock
import com.example.androidtemplateapp.entity.*
import com.example.androidtemplateapp.entity.enums.PokemonTypeEnum
import com.example.androidtemplateapp.entity.enums.StatNameEnum
import com.example.androidtemplateapp.ui.pokemonlist.PokemonListViewModel
import com.example.androidtemplateapp.ui.pokemonlist.details.PokemonDetailsViewModel
import com.example.androidtemplateapp.ui.settings.SettingsViewModel
import com.example.androidtemplateapp.ui.team.TeamViewModel


/****************************
 *******   VIEWMODEL  *******
 ***************************/
fun getPokemonListViewModelMock() = PokemonListViewModel(PokemonUseCaseImplMock())
fun getSettingsViewModelMock() = SettingsViewModel(UserDataUseCaseImplMock())
fun getPokemonDetailsViewModelMock() =
    PokemonDetailsViewModel(PokemonDetailsUseCaseImplMock(), SavedStateHandle())

fun getTeamViewModelMock() = TeamViewModel(PokemonUseCaseImplMock())

/****************************
 *********   DATA   *********
 ***************************/
fun getPokemonMock() =
    Pokemon(
        id = 1,
        url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
        name = "Bulbasour",
        dominantColor = null,
        isTeamMember = false
    )

fun getPokemonListMock() =
    listOf(
        Pokemon(
            id = 1,
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
            name = "Pokemon 1",
            dominantColor = null,
            isTeamMember = false
        ),
        Pokemon(
            id = 2,
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/2.png",
            name = "Pokemon 2",
            dominantColor = null,
            isTeamMember = false
        ),
        Pokemon(
            id = 3,
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/3.png",
            name = "Pokemon 3",
            dominantColor = null,
            isTeamMember = false
        ),
        Pokemon(
            id = 4,
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/2.png",
            name = "Pokemon 4",
            dominantColor = null,
            isTeamMember = false
        ),
        Pokemon(
            id = 5,
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/3.png",
            name = "Pokemon 5",
            dominantColor = null,
            isTeamMember = false
        ),
        Pokemon(
            id = 6,
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/3.png",
            name = "Pokemon 6",
            dominantColor = null,
            isTeamMember = false
        )
    )

fun getPokemonDetailsMock() =
    PokemonDetails(
        id = 1,
        order = 1,
        name = "Bulbasour",
        baseExperience = 64,
        height = 24,
        weight = 12,
        imageURL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
        stats = listOf(
            Stat(
                baseStat = 50, effort = 30, statX = StatX(StatNameEnum.ATTACK, "")
            ),
            Stat(
                baseStat = 80, effort = 70, statX = StatX(StatNameEnum.DEFENSE, "")
            ),
            Stat(
                baseStat = 70, effort = 10, statX = StatX(StatNameEnum.SPECIAL_ATTACK, "")
            ),
            Stat(
                baseStat = 100, effort = 30, statX = StatX(StatNameEnum.SPECIAL_DEFENSE, "")
            )
        ),
        types = listOf(
            TypeX(
                slot = 1, typeXX = TypeXX(PokemonTypeEnum.BUG, "")
            ), TypeX(
                slot = 2, typeXX = TypeXX(PokemonTypeEnum.POISON, "")
            )
        )
    )