package gob.pe.msi.trakingrealtime.data.model

import android.os.Parcel
import android.os.Parcelable
import gob.pe.msi.trakingrealtime.data.entity.RouteListResponseEntity
import java.util.ArrayList

data class HttpResponseRoutes(
    val CodigoRespuesta: String?,
    val Respuesta: String?,
    val Datos: ArrayList<RouteListResponseEntity>?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(RouteListResponseEntity)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(CodigoRespuesta)
        parcel.writeString(Respuesta)
        parcel.writeTypedList(Datos)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HttpResponseRoutes> {
        override fun createFromParcel(parcel: Parcel): HttpResponseRoutes {
            return HttpResponseRoutes(parcel)
        }

        override fun newArray(size: Int): Array<HttpResponseRoutes?> {
            return arrayOfNulls(size)
        }
    }
}