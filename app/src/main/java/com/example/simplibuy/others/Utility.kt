package com.example.simplibuy.others

import android.Manifest
import android.content.Context
import pub.devrel.easypermissions.EasyPermissions

object Utility {

    fun hasLocationPermissions(context: Context) =
        EasyPermissions.hasPermissions(
            context,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
}