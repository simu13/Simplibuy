package com.example.simplibuy.classes


import java.io.Serializable


data class SuperMArket(
    var Name:String = "",
    var Image:String = "",
    var menu:ArrayList<String> = arrayListOf()
    ):Serializable
