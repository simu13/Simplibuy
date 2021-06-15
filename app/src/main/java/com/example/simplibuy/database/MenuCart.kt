package com.example.simplibuy.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menu_items")
data class MenuCart(
    @ColumnInfo(name = "food_name")
    var name: String = "",
    @ColumnInfo(name = "food_amount")
    var amount: Int = 0,
    @ColumnInfo(name = "food_quantity")
    var quantity: Int = 0
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}