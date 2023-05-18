package com.example.androidbasetemplate.data.db.database

import androidx.room.RoomDatabase
import androidx.room.TypeConverters

// @Database(entities = [Reference::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    // abstract fun referenceDao(): ReferenceDao
}
