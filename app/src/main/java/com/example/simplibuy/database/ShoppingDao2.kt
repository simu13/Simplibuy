package com.example.simplibuy.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface
ShoppingDao2 {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: ShoppingItem)

    @Delete
    suspend fun delete(item: ShoppingItem)

    @Query("SELECT * FROM shopping_items")
    fun getAllShoppingItems(): LiveData<List<ShoppingItem>>
}

