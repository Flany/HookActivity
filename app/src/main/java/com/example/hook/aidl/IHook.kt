package com.example.hook.aidl

import android.os.Parcel
import android.os.Parcelable

data class IHook(val description: String, val type: Int) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readString() ?: "", parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(description)
        parcel.writeInt(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<IHook> {
        override fun createFromParcel(parcel: Parcel): IHook {
            return IHook(parcel)
        }

        override fun newArray(size: Int): Array<IHook?> {
            return arrayOfNulls(size)
        }
    }
}
