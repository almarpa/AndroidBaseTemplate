package com.example.androidtemplateapp.entity.enums

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
            entries.find { statNameEnum -> statNameEnum.value == name } ?: UNKNOWN
    }
}