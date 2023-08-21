package com.example.androidbasetemplate.data.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidbasetemplate.data.db.dao.ProductDao
import com.example.androidbasetemplate.entity.Product

@Database(entities = [Product::class], version = 2)
abstract class AppDataBase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}
