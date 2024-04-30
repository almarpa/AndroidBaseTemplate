package com.example.androidbasetemplate.entity.enum

enum class PokemonTypeEnum(val value: String) {
    NORMAL("normal"),
    FIRE("fire"),
    WATER("water"),
    ELECTRIC("electric"),
    GRASS("grass"),
    ICE("ice"),
    FIGHTING("fighting"),
    POISON("poison"),
    GROUND("ground"),
    FLYING("flying"),
    PSYCHIC("psychic"),
    BUG("bug"),
    ROCK("rock"),
    GHOST("ghost"),
    DRAGON("dragon"),
    DARK("dark"),
    STEEL("steel"),
    FAIRY("fairy"),
    UNKNOWN("unknown");

    companion object {
        fun from(name: String) =
            values().find { statNameEnum -> statNameEnum.value == name } ?: UNKNOWN
    }
}