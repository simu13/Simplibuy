package com.example.simplibuy.database

class ShoppingRepository(
    private val db: ShoppingDatabase
) {
    suspend fun upsert(item: Product) = db.getShoppingDao().upsert(item)

    suspend fun delete(item: Product) = db.getShoppingDao().delete(item)

    fun getAllShoppingItems() = db.getShoppingDao().getAllShopping()

    fun getSubTotal() = db.getShoppingDao().getSubTotal()

    fun getSubTotal2() = db.getShoppingDao().getSubTotal2()

    fun deleteAll() = db.getShoppingDao().deleteAll()
}