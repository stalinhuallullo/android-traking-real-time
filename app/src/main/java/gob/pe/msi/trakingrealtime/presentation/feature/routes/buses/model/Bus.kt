package gob.pe.msi.trakingrealtime.presentation.feature.routes.buses.model

import android.os.Parcel
import android.os.Parcelable


open class Bus(val id: String, val plate: String, val brand: String, var selected: Boolean = false) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readBoolean()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(plate)
        parcel.writeString(brand)
        parcel.writeBoolean(selected)
    }
    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Bus> {
        override fun createFromParcel(parcel: Parcel): Bus {
            return Bus(parcel)
        }

        override fun newArray(size: Int): Array<Bus?> {
            return arrayOfNulls(size)
        }
    }


}