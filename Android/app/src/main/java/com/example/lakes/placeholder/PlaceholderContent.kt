package com.example.lakes.placeholder

import android.os.Parcel
import android.os.Parcelable
import com.example.lakes.environment.LakePoint
import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object PlaceholderContent {

    /**
     * An array of sample (placeholder) items.
     */
    val ITEMS: MutableList<PlaceholderItem> = ArrayList()

    /**
     * A map of sample (placeholder) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, PlaceholderItem> = HashMap()

    fun clear() {
        ITEMS.clear()
        ITEM_MAP.clear()
    }

    fun addItem(item: PlaceholderItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.lakeID!!, item)
    }

    private fun createPlaceholderItem(position: Int): PlaceholderItem {
        return PlaceholderItem(position.toString(), "Item " + position, makeDetails(position), null)
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0 until position) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }

    /**
     * A placeholder item representing a piece of content.
     */
    data class PlaceholderItem(val lakeID: String?, val lakeName: String?, val lakeLocality: String?, var places: ArrayList<LakePoint>?)
        :Parcelable{

        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.createTypedArrayList(LakePoint.CREATOR)
        )

        override fun toString(): String = lakeName!!
        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(lakeID)
            parcel.writeString(lakeName)
            parcel.writeString(lakeLocality)
            parcel.writeTypedList(places)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<PlaceholderItem> {
            override fun createFromParcel(parcel: Parcel): PlaceholderItem {
                return PlaceholderItem(parcel)
            }

            override fun newArray(size: Int): Array<PlaceholderItem?> {
                return arrayOfNulls(size)
            }
        }


    }
}