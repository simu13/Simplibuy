package com.example.simplibuy.database

class ShoppingRepository2(
    private val db: ShoppingDatabase
) {
    suspend fun upsert(item: ShoppingItem) = db.getShoppingDao2().upsert(item)

    suspend fun delete(item: ShoppingItem) = db.getShoppingDao2().delete(item)

    fun getAllShoppingItems() = db.getShoppingDao2().getAllShoppingItems()
}