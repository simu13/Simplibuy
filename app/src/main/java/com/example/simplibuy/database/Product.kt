package com.example.simplibuy.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")

data class Product(
    @ColumnInfo(name = "item_name")
    var Name: String = "",
    @ColumnInfo(name = "item_Price")
    var Price: Int = 0,
    @ColumnInfo(name = "item_Weight")
    var Weight: Int = 0
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    }

