package com.example.simplibuy.classes

import android.os.Parcel
import android.os.Parcelable


data class SuperMArket(
    var Name:String = "",
    var Image:String = ""
    ):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Name)
        parcel.writeString(Image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SuperMArket> {
        override fun createFromParcel(parcel: Parcel): SuperMArket {
            return SuperMArket(parcel)
        }

        override fun newArray(size: Int): Array<SuperMArket?> {
            return arrayOfNulls(size)
        }
    }
}
