package com.example.androidbasetemplate.common.utils

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import com.example.androidbasetemplate.entity.Pokemon
import com.google.gson.GsonBuilder

inline fun <reified T : Parcelable> Bundle.getParcelableCompat(code: String, clazz: Class<T>) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelable(code, clazz)
    } else {
        getParcelable(code)
    }

fun String.getPokemonFromJson(): Pokemon =
    GsonBuilder().create().fromJson(this, Pokemon::class.java)


