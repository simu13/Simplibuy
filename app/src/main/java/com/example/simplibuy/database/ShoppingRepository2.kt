package com.example.simplibuy.database

class ShoppingRepository2(
    private val db: ShoppingDatabase
) {
    suspend fun upsert(item: ShoppingItem) = db.getShoppingDao2().upsert(item)

    suspend fun upsertFood(item:MenuCart) = db.getShoppingDao2().upsertFood(item)

    suspend fun deleteFood(item:MenuCart) = db.getShoppingDao2().deleteFood(item)

    suspend fun delete(item: ShoppingItem) = db.getShoppingDao2().delete(item)

    fun getAllShoppingItems() = db.getShoppingDao2().getAllShoppingItems()

    fun getAllMenuItems() = db.getShoppingDao2().getAllMenuItems()

     fun getTotalPrice() = db.getShoppingDao2().getTotalPrice()

    fun getTotalQuantity() = db.getShoppingDao2().getTotalQuantity()
}