package com.example.androidtemplateapp.ui.common.mocks

import com.example.androidtemplateapp.entity.*
import com.example.androidtemplateapp.entity.enums.PokemonTypeEnum
import com.example.androidtemplateapp.entity.enums.StatNameEnum

// region POKEMON_DATA_MOCKS
fun getPokemonMock() =
    Pokemon(
        id = 1,
        url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
        name = "Bulbasaur",
        dominantColor = null,
        isTeamMember = false
    )

fun getPokemonListMock() =
    listOf(
        Pokemon(
            id = 1,
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
            name = "CHARMELEON",
            dominantColor = null,
            isTeamMember = false
        ),
        Pokemon(
            id = 2,
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/2.png",
            name = "CHARMELEON CHARMELEON",
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
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/4.png",
            name = "Pokemon 4",
            dominantColor = null,
            isTeamMember = false
        ),
        Pokemon(
            id = 5,
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/5.png",
            name = "Pokemon 5",
            dominantColor = null,
            isTeamMember = false
        ),
        Pokemon(
            id = 6,
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/6.png",
            name = "Pokemon 6",
            dominantColor = null,
            isTeamMember = false
        ),
        Pokemon(
            id = 7,
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/7.png",
            name = "Pokemon 4",
            dominantColor = null,
            isTeamMember = false
        ),
        Pokemon(
            id = 8,
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/8.png",
            name = "Pokemon 5",
            dominantColor = null,
            isTeamMember = false
        ),
        Pokemon(
            id = 9,
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/9.png",
            name = "Pokemon 6",
            dominantColor = null,
            isTeamMember = false
        )
    )

fun getPokemonDetailsMock() =
    PokemonDetails(
        id = 1,
        order = 1,
        name = "Bulbasaur",
        baseExperience = 64,
        height = 24,
        weight = 12,
        imageURL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
        stats = getPokemonStatListMock(),
        types = getPokemonTypeListMock(),
        moves = getPokemonMoveListMock()
    )

fun getPokemonStatListMock() =
    listOf(
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
    )

fun getPokemonTypeListMock() =
    listOf(
        TypeX(
            slot = 1, typeXX = TypeXX(PokemonTypeEnum.BUG, "")
        ), TypeX(
            slot = 2, typeXX = TypeXX(PokemonTypeEnum.POISON, "")
        )
    )

fun getPokemonMoveListMock() =
    listOf(
        Move(
            move = MoveX(
                name = "Tackle 1", url = "https://pokeapi.co/api/v2/move/33/"
            )
        ),
        Move(
            move = MoveX(
                name = "Tackle 2 Tackle 2", url = "https://pokeapi.co/api/v2/move/33/"
            )
        ),
        Move(
            move = MoveX(
                name = "Tackle 3 Tackle 3 Tackle 3", url = "https://pokeapi.co/api/v2/move/33/"
            )
        ),
        Move(
            move = MoveX(
                name = "Tackle 4 Tackle 4 Tackle 4 Tackle 4",
                url = "https://pokeapi.co/api/v2/move/33/"
            )
        )
    )
// endregion