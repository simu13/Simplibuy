package com.example.simplibuy.activties

import android.content.Context

object Preferences{
    const val USERROLE = "userRole"
    const val ROLE = "role"
    const val NOROLE = "NOROLE"
    const val BUSINESS = "BUSINESS"
    const val CUSTOMER = "CUSTOMER"
    const val FINISHED = "Finished"
    const val ONBOARDING = "onBoarding"

    fun Context.userRole(): String {
        val sharedPref = getSharedPreferences(USERROLE, Context.MODE_PRIVATE)
        return sharedPref.getString(ROLE, NOROLE) ?: NOROLE
    }

    fun Context.updateRole(role:String){
        val sharedPref = getSharedPreferences(USERROLE, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(ROLE, role)
        editor.apply()
    }
}


