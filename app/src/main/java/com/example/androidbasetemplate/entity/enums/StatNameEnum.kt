package com.example.androidbasetemplate.entity.enums

enum class StatNameEnum(val value: String) {
    HP("hp"),
    ATTACK("attack"),
    DEFENSE("defense"),
    SPECIAL_ATTACK("special-attack"),
    SPECIAL_DEFENSE("special-defense"),
    SPEED("speed"),
    UNKNOWN("unknown");

    companion object {
        fun from(name: String) =
            values().find { statNameEnum -> statNameEnum.value == name } ?: UNKNOWN
    }
}