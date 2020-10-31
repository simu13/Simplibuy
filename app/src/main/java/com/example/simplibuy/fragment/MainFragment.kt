package com.example.simplibuy.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.simplibuy.R
import com.example.simplibuy.activties.Final
import com.example.simplibuy.activties.ShoppingActivity
import com.example.simplibuy.activties.ZxingScanner
import com.example.simplibuy.classes.Firebase
import com.example.simplibuy.database.ShoppingDatabase
import com.example.simplibuy.database.ShoppingRepository
import com.example.simplibuy.database.ShoppingViewModel
import com.example.simplibuy.database.ShoppingViewModelFactory
import com.example.simplibuy.model.User
import kotlinx.android.synthetic.main.activity_bill.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*


import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainFragment : Fragment() {
    lateinit var viewModel : ShoppingViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val database = activity?.let { ShoppingDatabase(it) }
        val repository =
            database?.let { ShoppingRepository(it) }
        val factory =
            repository?.let { ShoppingViewModelFactory(it) }

        viewModel = factory?.let { ViewModelProvider(this, it).get(ShoppingViewModel::class.java) }!!

        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        root.additemButton.setOnClickListener {
            val intent = Intent(activity,ZxingScanner::class.java)
            startActivity(intent)
        }
        root.checkoutButton.setOnClickListener {
            val intent = Intent(activity,Final::class.java)
            startActivity(intent)
        }
        root.shoppingListButton.setOnClickListener {
            val intent = Intent(activity,ShoppingActivity::class.java)
            startActivity(intent)
        }
       (activity as AppCompatActivity).supportActionBar?.show()
        Firebase().signInUser(this)

        GlobalScope.launch {
            val totalPrice = viewModel.getSubTotal().toString()
            val totalWeight = viewModel.getSubTotal2().toString()
            //root.
            root.tv_number_items.text = "Rs. $totalPrice"
            root.tv_bill_number.text = "$totalWeight Wg"
        }

        return root
    }

    fun signInSuccess(user: User){
        tv_full_name.text = user.firstName
        tv_user_name.text = user.email
        Glide.with(this).load(user.image).into(profile_image)
    }
}


