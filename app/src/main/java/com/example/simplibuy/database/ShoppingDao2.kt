package com.example.simplibuy.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface
ShoppingDao2 {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: ShoppingItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertFood(item: MenuCart)

    @Delete
    suspend fun delete(item: ShoppingItem)

    @Delete
    suspend fun deleteFood(item: MenuCart)

    @Query("SELECT * FROM shopping_items")
    fun getAllShoppingItems(): LiveData<List<ShoppingItem>>

    @Query("SELECT SUM(food_amount * food_quantity) FROM menu_items")
    fun getTotalPrice(): LiveData<Int>

    @Query("SELECT SUM(food_quantity) FROM menu_items")
    fun getTotalQuantity(): LiveData<Int>

    @Query("SELECT * FROM menu_items")
    fun getAllMenuItems(): LiveData<List<MenuCart>>
}

