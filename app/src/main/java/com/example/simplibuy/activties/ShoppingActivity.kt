package com.example.simplibuy.activties

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplibuy.R
import com.example.simplibuy.database.*
import com.example.simplibuy.others.Utility
import kotlinx.android.synthetic.main.activity_shopping.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


class ShoppingActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions()
        setContentView(R.layout.activity_shopping)

        val database = ShoppingDatabase(this)
        val repository =
            ShoppingRepository2(database)
        val factory =
            ShoppingViewModelFactory2(repository)
        val viewModel = ViewModelProvider(this, factory).get(ShoppingViewModel2::class.java)

        val adapter = ShoppingItemAdapter2(
            listOf(),
            viewModel
        )

        rvShoppingItems.layoutManager = LinearLayoutManager(this)
        rvShoppingItems.adapter = adapter

        viewModel.getAllShoppingItems().observe(this, Observer{
            adapter.items = it
            adapter.notifyDataSetChanged()
        })

        fab.setOnClickListener{
            AddShoppingItemDialog(this,
                object : AddDialogListener {
                    override fun onAddButtonClicked(item: ShoppingItem) {
                        viewModel.upsert(item)
                    }
                }).show()
        }
    }
    private fun requestPermissions(){
        if(Utility.hasLocationPermissions(this))
        {
            return
        }
        else
        {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept this Permissions to use this App",
                0,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}