package com.example.androidbasetemplate.entity

data class Species(
    val name: String,
    val url: String,
) {
    constructor() : this("", "")
}
