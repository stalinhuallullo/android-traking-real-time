package gob.pe.msi.trakingrealtime.domain

import android.os.Parcel
import android.os.Parcelable
import gob.pe.msi.trakingrealtime.presentation.feature.routes.routerList.model.dto.RouteDto

open class RoutePresentation() : Parcelable {


    var presentationId: Int? = 0
        get() = field ?: 1
    var isUnitPresentationDefault: Boolean? = false
    var presentationSelected: Boolean? = false

    var routeName: String? = null
    var route: String? = null
    var direction: String? = null

    constructor(routeDto: RouteDto) : this() {
        routeName = routeDto.routeName
        route = routeDto.route
        direction = routeDto.direction
    }
    constructor(parcel: Parcel) : this() {

        presentationId = if (parcel.readByte().toInt() == 0) {
            null
        } else {
            parcel.readInt()
        }

        isUnitPresentationDefault = if (parcel.readByte().toInt() == 0) {
            null
        } else {
            parcel.readByte().toInt() != 0
        }

        presentationSelected = if (parcel.readByte().toInt() == 0) {
            null
        } else {
            parcel.readByte().toInt() != 0
        }

        routeName = parcel.readString()
        route = parcel.readString()
        direction = parcel.readString()

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        presentationId?.let {
            parcel.writeByte(1.toByte())
            parcel.writeInt(it)
        } ?: parcel.writeByte(0.toByte())

        isUnitPresentationDefault?.let {
            parcel.writeByte(1.toByte())
            parcel.writeByte((if (it) 1 else 0).toByte())
        } ?: parcel.writeByte(0.toByte())

        presentationSelected?.let {
            parcel.writeByte(1.toByte())
            parcel.writeByte((if (it) 1 else 0).toByte())
        } ?: parcel.writeByte(0.toByte())

        parcel.writeString(routeName)
        parcel.writeString(route)
        parcel.writeString(direction)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RoutePresentation> {
        override fun createFromParcel(parcel: Parcel): RoutePresentation {
            return RoutePresentation(parcel)
        }

        override fun newArray(size: Int): Array<RoutePresentation?> {
            return arrayOfNulls(size)
        }
    }
}