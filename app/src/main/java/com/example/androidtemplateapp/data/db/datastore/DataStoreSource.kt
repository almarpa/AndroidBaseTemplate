package com.example.androidtemplateapp.data.db.datastore

import kotlinx.coroutines.flow.Flow

interface DataStoreSource {

    suspend fun putString(key: String, value: String)
    fun getString(key: String): Flow<String?>
}