package com.example.androidbasetemplate.data.db.database

import android.content.SharedPreferences
import javax.inject.Singleton

@Singleton
class SharedPreferencesSource(private val sharedPreferences: SharedPreferences) {

    companion object {
        const val DARK_MODE = "DARK_MODE"
    }

    fun storeStringData(key: String, value: String?) {
        val edit = sharedPreferences.edit()
        edit.putString(key, value)
        edit.apply()
    }

    fun storeIntData(key: String, value: Int) {
        val edit = sharedPreferences.edit()
        edit.putInt(key, value)
        edit.apply()
    }

    fun storeBooleanData(key: String, value: Boolean) {
        val edit = sharedPreferences.edit()
        edit.putBoolean(key, value)
        edit.apply()
    }

    fun getStringData(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun getIntData(key: String): Int {
        return sharedPreferences.getInt(key, 0)
    }

    fun getBooleanData(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun removeData(key: String) {
        val edit = sharedPreferences.edit()
        edit.remove(key)
        edit.apply()
    }
}
