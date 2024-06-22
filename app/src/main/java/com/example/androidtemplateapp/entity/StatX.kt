package com.example.androidtemplateapp.entity

import androidx.compose.ui.graphics.Color
import com.example.androidtemplateapp.R
import com.example.androidtemplateapp.entity.enums.StatNameEnum
import com.example.androidtemplateapp.ui.theme.*

data class StatX(
    val name: StatNameEnum,
    val url: String,
) {
    fun getColor(): Color {
        return when (name) {
            StatNameEnum.HP -> HPColor
            StatNameEnum.ATTACK -> AtkColor
            StatNameEnum.DEFENSE -> DefColor
            StatNameEnum.SPECIAL_ATTACK -> SpAtkColor
            StatNameEnum.SPECIAL_DEFENSE -> SpDefColor
            StatNameEnum.SPEED -> SpdColor
            StatNameEnum.UNKNOWN -> Color.White
        }
    }

    fun getAbbreviation(): Int {
        return when (name) {
            StatNameEnum.HP -> R.string.stat_hp
            StatNameEnum.ATTACK -> R.string.stat_attack
            StatNameEnum.DEFENSE -> R.string.stat_defense
            StatNameEnum.SPECIAL_ATTACK -> R.string.stat_special_attack
            StatNameEnum.SPECIAL_DEFENSE -> R.string.stat_special_defense
            StatNameEnum.SPEED -> R.string.stat_speed
            StatNameEnum.UNKNOWN -> R.string.empty_string
        }
    }
}
