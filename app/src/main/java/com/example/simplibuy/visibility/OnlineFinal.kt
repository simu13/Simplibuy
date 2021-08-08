package com.example.simplibuy.visibility

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.simplibuy.R
import com.example.simplibuy.database.*
import kotlinx.android.synthetic.main.activity_bill.*
import kotlinx.android.synthetic.main.activity_bill.tv_bill_number
import kotlinx.android.synthetic.main.activity_bill.tv_items
import kotlinx.android.synthetic.main.activity_bill.tv_number_items
import kotlinx.android.synthetic.main.activity_bill_online.*
import kotlinx.android.synthetic.main.fragment_info.*

import kotlinx.android.synthetic.main.zxing.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class OnlineFinal : AppCompatActivity()

{
    lateinit var viewModel : ShoppingViewModel2
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill)
        val database = ShoppingDatabase(this@OnlineFinal)
        val repository =
            ShoppingRepository2(database)
        val factory =
            ShoppingViewModelFactory2(repository)

        viewModel = ViewModelProvider(this, factory).get(ShoppingViewModel2::class.java)
        viewModel.getTotalPrice().observeForever{
            tv_number_items?.text = it?.toString()?:"0"
        }
        viewModel.getTotalQuantity().observeForever {

            tv_bill_number?.text = it?.toString()?:"0"

        }
        /*GlobalScope.launch {

            val totalWeight = viewModel.getTotalQuantity().toString()
           // tv_number_items.text = "Rs.$totalPrice"
            tv_bill_number.text = "$totalWeight "

        }*/

/*payment.setOnClickListener {

            GlobalScope.launch {
                viewModel.deleteAll()
                //Toast.makeText(this@Final,"Done",Toast.LENGTH_SHORT).show()
            }
        }*/

    }
}