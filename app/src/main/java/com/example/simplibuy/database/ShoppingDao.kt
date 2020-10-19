package com.example.simplibuy.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface
ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: Product)

    @Delete
    suspend fun delete(item: Product)

    @Query("SELECT * FROM items")
    fun getAllShopping(): LiveData<List<Product>>

    @Query("SELECT SUM(item_Price) FROM items")
    fun getSubTotal():Int

    @Query("SELECT SUM(item_Weight) FROM items")
    fun getSubTotal2():Int

    @Query("DELETE FROM items")
    fun deleteAll()

}

