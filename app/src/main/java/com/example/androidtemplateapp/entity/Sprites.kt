package com.example.androidtemplateapp.entity

data class Sprites(
    val backDefault: String?,
    val backFemale: Any?,
    val backShiny: String?,
    val backShinyFemale: Any?,
    val frontDefault: String?,
    val frontFemale: Any?,
    val frontShiny: String?,
    val frontShinyFemale: Any?,
) {
    constructor() : this(
        backDefault = "",
        backFemale = null,
        backShiny = "",
        backShinyFemale = null,
        frontDefault = "",
        frontFemale = null,
        frontShiny = "",
        frontShinyFemale = null
    )
}
