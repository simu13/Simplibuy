package com.example.simplibuy.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ShoppingViewModel2(

    private val repository: ShoppingRepository2
)

    : ViewModel() {

    fun upsert(item: ShoppingItem) =
        GlobalScope.launch {

            repository.upsert(item)
        }

    fun upsertFood(item: MenuCart) =
        GlobalScope.launch {
            repository.upsertFood(item)
        }

    fun deleteFodd(item: MenuCart) =

        GlobalScope.launch {

            repository.deleteFood(item)
        }

    fun delete(item: ShoppingItem) = GlobalScope.launch {
        repository.delete(item)
    }
    fun getAllShoppingItems() = repository.getAllShoppingItems()

    fun getAllMenuItems() = repository.getAllMenuItems()

    fun  getTotalPrice() = repository.getTotalPrice()

}
