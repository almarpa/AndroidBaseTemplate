package com.example.androidbasetemplate.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class Product(
    @PrimaryKey
    @ColumnInfo(name = "productId")
    var productId: String,
    @ColumnInfo(name = "denomination") var denomination: String,
    @ColumnInfo(name = "imageUrl") var imageUrl: String,
)
