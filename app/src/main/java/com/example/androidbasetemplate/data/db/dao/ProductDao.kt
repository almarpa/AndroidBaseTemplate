package com.example.androidbasetemplate.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.androidbasetemplate.entity.Product

@Dao
interface ProductDao {

    @Query("SELECT * from product WHERE productId = :id")
    fun get(id: String): Product?

    @Query("SELECT * from product")
    fun getAll(): List<Product>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(productList: List<Product>)

    @Update
    fun update(product: Product)

    @Delete
    fun delete(product: Product)

    @Query("DELETE FROM product")
    fun clearProducts()
}
