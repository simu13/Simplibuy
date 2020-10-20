package com.example.simplibuy.database

import androidx.lifecycle.ViewModel
import com.example.simplibuy.database.Product
import com.example.simplibuy.database.ShoppingRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ShoppingViewModel(
    private val repository: ShoppingRepository
) : ViewModel() {

    fun upsert(item: Product) =
        GlobalScope.launch {
            repository.upsert(item)
        }

    fun delete(item: Product) = GlobalScope.launch {
        repository.delete(item)
    }

    fun getAllShoppingItems() = repository.getAllShoppingItems()

    fun getSubTotal() = repository.getSubTotal()

    fun getSubTotal2() = repository.getSubTotal2()

    fun deleteAll() = repository.deleteAll()

}
