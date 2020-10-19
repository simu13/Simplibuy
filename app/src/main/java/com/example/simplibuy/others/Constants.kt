package com.example.simplibuy.others

import android.graphics.Color

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
}