package com.example.simplibuy.activties

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.simplibuy.R
import com.example.simplibuy.database.ShoppingDatabase
import com.example.simplibuy.database.ShoppingRepository
import com.example.simplibuy.database.ShoppingViewModel
import com.example.simplibuy.database.ShoppingViewModelFactory
import kotlinx.android.synthetic.main.activity_bill.*

import kotlinx.android.synthetic.main.zxing.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Final : AppCompatActivity()

{
    lateinit var viewModel : ShoppingViewModel
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill)
        val database = ShoppingDatabase(this@Final)
        val repository =
            ShoppingRepository(database)
        val factory =
            ShoppingViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory).get(ShoppingViewModel::class.java)

        GlobalScope.launch {
            val totalPrice = viewModel.getSubTotal().toString()
            val totalWeight = viewModel.getSubTotal2().toString()
            tv_number_items.text = "Rs. $totalPrice"
            tv_bill_number.text = "$totalWeight Wg" }

/*payment.setOnClickListener {

            GlobalScope.launch {
                viewModel.deleteAll()
                //Toast.makeText(this@Final,"Done",Toast.LENGTH_SHORT).show()
            }
        }*/

    }
}