package com.example.androidbasetemplate.ui.common.navigation.navtypes

import android.os.Bundle
import androidx.navigation.NavType
import com.example.androidbasetemplate.common.utils.getParcelableCompat
import com.example.androidbasetemplate.entity.Pokemon
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val PokemonNavType = object : NavType<Pokemon>(
    isNullableAllowed = false
) {
    override fun get(bundle: Bundle, key: String): Pokemon? {
        return bundle.getParcelableCompat(key, Pokemon::class.java)
    }

    override fun parseValue(value: String): Pokemon {
        return Json.decodeFromString<Pokemon>(value)
    }

    override fun serializeAsValue(value: Pokemon): String {
        return Json.encodeToString(value)
    }

    override fun put(bundle: Bundle, key: String, value: Pokemon) {
        bundle.putParcelable(key, value)
    }
}