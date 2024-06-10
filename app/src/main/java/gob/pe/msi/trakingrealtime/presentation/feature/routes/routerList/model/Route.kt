package gob.pe.msi.trakingrealtime.presentation.feature.routes.routerList.model

import android.os.Parcel
import android.os.Parcelable

data class Route(val routeName: String, val route: String, val direction: String, var selected: Boolean = false) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readBoolean()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(routeName)
        parcel.writeString(route)
        parcel.writeString(direction)
        parcel.writeBoolean(selected)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Route> {
        override fun createFromParcel(parcel: Parcel): Route {
            return Route(parcel)
        }

        override fun newArray(size: Int): Array<Route?> {
            return arrayOfNulls(size)
        }
    }
}