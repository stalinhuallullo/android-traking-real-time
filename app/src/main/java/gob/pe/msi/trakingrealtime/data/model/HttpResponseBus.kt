package gob.pe.msi.trakingrealtime.data.model

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

data class HttpResponseBus(
    val CodigoRespuesta: String?,
    val Respuesta: String?,
    val Datos: ArrayList<BusDataModel>?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(BusDataModel)
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

    companion object CREATOR : Parcelable.Creator<HttpResponseBus> {
        override fun createFromParcel(parcel: Parcel): HttpResponseBus {
            return HttpResponseBus(parcel)
        }

        override fun newArray(size: Int): Array<HttpResponseBus?> {
            return arrayOfNulls(size)
        }
    }
}