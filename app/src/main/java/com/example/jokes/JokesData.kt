package com.example.jokes

import android.os.Parcel
import android.os.Parcelable

data class JokesData(
    val id: Int,
    val punchline: String?,
    val setup: String?,
    val type: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(punchline)
        parcel.writeString(setup)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<JokesData> {
        override fun createFromParcel(parcel: Parcel): JokesData {
            return JokesData(parcel)
        }

        override fun newArray(size: Int): Array<JokesData?> {
            return arrayOfNulls(size)
        }
    }
}