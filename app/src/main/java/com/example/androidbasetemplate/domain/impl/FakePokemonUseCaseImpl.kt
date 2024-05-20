package com.example.androidbasetemplate.domain.impl

import com.example.androidbasetemplate.domain.PokemonUseCase
import com.example.androidbasetemplate.entity.*
import com.example.androidbasetemplate.entity.enums.PokemonTypeEnum
import com.example.androidbasetemplate.entity.enums.StatNameEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakePokemonUseCaseImpl : PokemonUseCase {

    override suspend fun getPokemons(): Flow<List<Pokemon>> {
        return flowOf()
    }

    override suspend fun getPokemon(pokemonID: Int): PokemonDetails {
        return PokemonDetails(
            id = 1,
            order = 1,
            name = "Bulbasour",
            baseExperience = 64,
            height = 24,
            weight = 12,
            imageURL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
            stats = listOf(
                Stat(
                    baseStat = 50,
                    effort = 30,
                    statX = StatX(StatNameEnum.ATTACK, "")
                ),
                Stat(
                    baseStat = 80,
                    effort = 70,
                    statX = StatX(StatNameEnum.DEFENSE, "")
                ),
                Stat(
                    baseStat = 80,
                    effort = 70,
                    statX = StatX(name = StatNameEnum.HP, url = "")
                )
            ),
            types = listOf(
                TypeX(
                    slot = 1,
                    typeXX = TypeXX(PokemonTypeEnum.BUG, "")
                ),
                TypeX(
                    slot = 2,
                    typeXX = TypeXX(PokemonTypeEnum.POISON, "")
                )
            )
        )
    }
}
