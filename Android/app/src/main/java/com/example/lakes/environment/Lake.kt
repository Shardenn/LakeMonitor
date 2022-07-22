package com.example.lakes.environment

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class Lake(
    @field:Json(name = "Jarvi_Id") val id: Int, // Lake
    @field:Json(name = "Nro") val number: String?, // Number
    @field:Json(name = "Nimi") val name: String?, // Name
    @field:Json(name = "H_Kunta_Id") val municipality_id: Int, // Municipality id
    @field:Json(name = "KuntaNimi") val municipalityName: String?, // Municipality name
    @field:Json(name = "KoordErLat") val lat: String?, // Latitude
    @field:Json(name = "KoordErLong")val long: String?, // Longitude
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(number)
        parcel.writeString(name)
        parcel.writeInt(municipality_id)
        parcel.writeString(municipalityName)
        parcel.writeString(lat)
        parcel.writeString(long)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Lake> {
        override fun createFromParcel(parcel: Parcel): Lake {
            return Lake(parcel)
        }

        override fun newArray(size: Int): Array<Lake?> {
            return arrayOfNulls(size)
        }
    }
}

data class LakePoint(
    @field:Json(name = "Paikka_Id") val id: Int,
    @field:Json(name = "Suure_Id") val id_big: Int,
    @field:Json(name = "H_Kunta_Id") val id_municipality: Int,
    @field:Json(name = "KuntaNimi") val municipalityName: String?,
    @field:Json(name = "Nimi") val name: String?,
    @field:Json(name = "Jarvi_Id") val id_lake: Int,
    @field:Json(name = "JarviNimi") val lakeName: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(id_big)
        parcel.writeInt(id_municipality)
        parcel.writeString(municipalityName)
        parcel.writeString(name)
        parcel.writeInt(id_lake)
        parcel.writeString(lakeName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LakePoint> {
        override fun createFromParcel(parcel: Parcel): LakePoint {
            return LakePoint(parcel)
        }

        override fun newArray(size: Int): Array<LakePoint?> {
            return arrayOfNulls(size)
        }
    }
}