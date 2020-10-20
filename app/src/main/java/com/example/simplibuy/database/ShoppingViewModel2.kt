package com.example.simplibuy.database

import androidx.lifecycle.ViewModel
import com.example.simplibuy.database.ShoppingItem
import com.example.simplibuy.database.ShoppingRepository2
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ShoppingViewModel2(
    private val repository: ShoppingRepository2
) : ViewModel() {

    fun upsert(item: ShoppingItem) =
        GlobalScope.launch {
            repository.upsert(item)
        }

    fun delete(item: ShoppingItem) = GlobalScope.launch {
        repository.delete(item)
    }

    fun getAllShoppingItems() = repository.getAllShoppingItems()

}
