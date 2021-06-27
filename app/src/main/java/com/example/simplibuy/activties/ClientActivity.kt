package com.example.simplibuy.activties

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.simplibuy.R
import com.example.simplibuy.seller.authentication.AuthenticationActivity
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_client.*


class ClientActivity : AppCompatActivity(){

    private var doubleBackToExitPressedOnce = false
    private lateinit var drawerLayout: DrawerLayout

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)
        val toolbar: Toolbar = findViewById(R.id.clientToolbar)
        setSupportActionBar(toolbar)
        drawerLayout = findViewById(R.id.client_drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view_client_activity)
        val navController = findNavController(R.id.ClientfragmentNavHost)
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.sellerMainFragment
        ), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }



    override fun onBackPressed() {
        if (client_drawer_layout.isDrawerOpen(GravityCompat.START)) {
            client_drawer_layout.closeDrawer(GravityCompat.START) }
        else
        {
            // A double back press function is added in Base Activity.
            doubleBackToExit()
        }
    }


    fun doubleBackToExit() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(
            this,
            resources.getString(R.string.please_click_back_again_to_exit),
            Toast.LENGTH_SHORT
        ).show()

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.ClientfragmentNavHost)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, AuthenticationActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

}