package com.example.simplibuy.others

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import androidx.fragment.app.Fragment
import com.example.simplibuy.fragment.ProfileFragment

object Constants {
    const val RUNNING_DATABASE_NAME = "running_database"
    const val REQUEST_CODE_LOCATION_PERMISSION = 0
    const val ACTION_STATE_OR_RESUME = "ACTION_STATE_OR_RESUME"
    const val ACTION_PAUSE_SERVICE = "ACTION_PAUSE_SERVICE"
    const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"
    const val ACTION_SHOW_TRACKING_FRAGMENT = "ACTION_SHOW_TRACKING_FRAGMENT"
    const val ACTION_SHOW_SETUP_FRAGMENT = "ACTION_SHOW_SETUP_FRAGMENT"
    const val NOTIFICATION_CHANNEL_ID = "tracking_channel"
    const val NOTIFICATION_CHANNEL_NAME = "tracking"
    const val NOTIFICATION_ID = 1
    const val POLYLINE_COLOR = Color.RED
    const val POLYLINE_WIDTH = 8f
    const val MAP_ZOOM = 15f
    const val LOCATION_UPDATE_TIME = 5000L
    const val FASTEST_UPDATE_TIME = 2000L
    const val TIMER_UPDATE_INTERVAL = 50L
    const val USERS: String = "users"
    const val IMAGE: String = "image"
    const val NAME: String = "firstName"
    const val PICK_IMAGE_REQUEST_CODE = 2

    fun showImageChooser(activity: Fragment) {
        // An intent for launching the image selection of phone storage.
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        // Launches the image selection of phone storage using the constant code.
        activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }

    fun getFileExtension(activity: Activity, uri: Uri?): String? {
        /*
         * MimeTypeMap: Two-way map that maps MIME-types to file extensions and vice versa.
         *
         * getSingleton(): Get the singleton instance of MimeTypeMap.
         *
         * getExtensionFromMimeType: Return the registered extension for the given MIME type.
         *
         * contentResolver.getType: Return the MIME type of the given content URL.
         */
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }
}