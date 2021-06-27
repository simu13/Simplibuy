package com.example.simplibuy.activties

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.simplibuy.R
import com.example.simplibuy.seller.authentication.AuthenticationActivity
import com.google.firebase.auth.FirebaseAuth

class ShoppingCartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)
    }

}