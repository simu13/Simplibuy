package com.example.simplibuy.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Product::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ShoppingDatabase :RoomDatabase(){
    abstract fun getRunDao():ShoppingDao
}