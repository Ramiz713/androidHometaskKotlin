package com.itis2018hometask.itis2018hometask

import android.os.Parcel
import android.os.Parcelable

data class SongItem(var name: String,
                    var artist: String,
                    var image: Int,
                    var album: String,
                    var source: Int) : Parcelable {
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

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SongItem> {
        override fun createFromParcel(parcel: Parcel): SongItem {
            return SongItem(parcel)
        }

        override fun newArray(size: Int): Array<SongItem?> {
            return arrayOfNulls(size)
        }
    }
}
