package com.example.androidtemplateapp.entity

data class Species(
    val name: String,
    val url: String,
) {
    constructor() : this("", "")
}
