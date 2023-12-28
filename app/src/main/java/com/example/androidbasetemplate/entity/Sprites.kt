package com.example.androidbasetemplate.entity

data class Sprites(
    val back_default: String,
    val back_female: Any?,
    val back_shiny: String,
    val back_shiny_female: Any?,
    val front_default: String,
    val front_female: Any?,
    val front_shiny: String,
    val front_shiny_female: Any?,
) {
    constructor() : this(
        back_default = "",
        back_female = null,
        back_shiny = "",
        back_shiny_female = null,
        front_default = "",
        front_female = null,
        front_shiny = "",
        front_shiny_female = null
    )
}
