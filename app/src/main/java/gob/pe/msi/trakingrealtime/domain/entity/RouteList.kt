package gob.pe.msi.trakingrealtime.domain.entity

import android.os.Parcel
import android.os.Parcelable


data class RouteList(
    var codLinea: String? = null,
    var linea: String? = null,
    var rgbBus: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        codLinea = parcel.readString(),
        linea = parcel.readString(),
        rgbBus = parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(codLinea)
        parcel.writeString(linea)
        parcel.writeString(rgbBus)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<RouteList> {
        override fun createFromParcel(parcel: Parcel): RouteList {
            return RouteList(parcel)
        }

        override fun newArray(size: Int): Array<RouteList?> {
            return arrayOfNulls(size)
        }
    }
}
