package com.example.androidbasetemplate.entity

import androidx.compose.ui.graphics.Color
import com.example.androidbasetemplate.entity.enum.PokemonTypeEnum
import com.example.androidbasetemplate.ui.theme.*

data class TypeXX(
    val name: PokemonTypeEnum,
    val url: String,
) {
    fun getColor(): Color {
        return when (name) {
            PokemonTypeEnum.NORMAL -> TypeNormal
            PokemonTypeEnum.FIRE -> TypeFire
            PokemonTypeEnum.WATER -> TypeWater
            PokemonTypeEnum.ELECTRIC -> TypeElectric
            PokemonTypeEnum.GRASS -> TypeGrass
            PokemonTypeEnum.ICE -> TypeIce
            PokemonTypeEnum.FIGHTING -> TypeFighting
            PokemonTypeEnum.POISON -> TypePoison
            PokemonTypeEnum.GROUND -> TypeGround
            PokemonTypeEnum.FLYING -> TypeFlying
            PokemonTypeEnum.PSYCHIC -> TypePsychic
            PokemonTypeEnum.BUG -> TypeBug
            PokemonTypeEnum.ROCK -> TypeRock
            PokemonTypeEnum.GHOST -> TypeGhost
            PokemonTypeEnum.DRAGON -> TypeDragon
            PokemonTypeEnum.DARK -> TypeDark
            PokemonTypeEnum.STEEL -> TypeSteel
            PokemonTypeEnum.FAIRY -> TypeFairy
            PokemonTypeEnum.UNKNOWN -> Color.Black
        }
    }
}
