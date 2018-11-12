package com.itis2018hometask.itis2018hometask

import android.os.Parcel
import android.os.Parcelable

data class SongItem(val name: String,
                    val artist: String,
                    val image: Int,
                    val album: String,
                    val source: Int) : Parcelable {

    companion object CREATOR : Parcelable.Creator<SongItem> {
        override fun createFromParcel(parcel: Parcel): SongItem {
            return SongItem(parcel)
        }

        override fun newArray(size: Int): Array<SongItem?> {
            return arrayOfNulls(size)
        }
    }

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(artist)
        parcel.writeInt(image)
        parcel.writeString(album)
        parcel.writeInt(source)
    }

    override fun describeContents(): Int = 0
}
