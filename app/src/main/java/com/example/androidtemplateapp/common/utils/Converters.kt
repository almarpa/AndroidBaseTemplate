package com.example.androidtemplateapp.common.utils

import androidx.room.TypeConverter
import com.example.androidtemplateapp.entity.Move
import com.example.androidtemplateapp.entity.Stat
import com.example.androidtemplateapp.entity.TypeX
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun stringToStatList(data: String): List<Stat> {
        val listType = object : TypeToken<List<Stat>>() {}.type
        return GsonBuilder().create().fromJson(data, listType)
    }

    @TypeConverter
    fun statListToString(statList: List<Stat>): String {
        return GsonBuilder().create().toJson(statList)
    }

    @TypeConverter
    fun stringToTypeList(data: String): List<TypeX> {
        val listType = object : TypeToken<List<TypeX>>() {}.type
        return GsonBuilder().create().fromJson(data, listType)
    }

    @TypeConverter
    fun typeListToString(typeList: List<TypeX>): String {
        return GsonBuilder().create().toJson(typeList)
    }

    @TypeConverter
    fun stringToMoveList(data: String): List<Move> {
        val listType = object : TypeToken<List<Move>>() {}.type
        return GsonBuilder().create().fromJson(data, listType)
    }

    @TypeConverter
    fun moveListToString(typeList: List<Move>): String {
        return GsonBuilder().create().toJson(typeList)
    }
}