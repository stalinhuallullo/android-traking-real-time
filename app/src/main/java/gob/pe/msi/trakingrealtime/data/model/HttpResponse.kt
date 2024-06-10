package gob.pe.msi.trakingrealtime.data.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class HttpResponse<T>(
    val CodigoRespuesta: String,
    val Respuesta: String,
    val Datos: Any?,
) : Parcelable {
    val datosList: T?
        get() = if (Datos is List<*>) {
            @Suppress("UNCHECKED_CAST")
            Datos as? T
        } else {
            null
        }

    val datosObject: T?
        get() = if (Datos !is List<*>) {
            @Suppress("UNCHECKED_CAST")
            Datos as? T
        } else {
            null
        }

    /*val singleLevelDatosList: List<T>?
        get() = when {
            Datos is List<*> -> {
                val list = Datos as List<*>
                if (list.isNotEmpty() && list[0] is List<*>) {
                    @Suppress("UNCHECKED_CAST")
                    (list[0] as? List<T>)
                } else {
                    @Suppress("UNCHECKED_CAST")
                    list as? List<T>
                }
            }
            else -> null
        }*/

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readValue(Any::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(CodigoRespuesta)
        parcel.writeString(Respuesta)
        parcel.writeValue(Datos)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<HttpResponse<*>> {
        override fun createFromParcel(parcel: Parcel): HttpResponse<*> {
            return HttpResponse<Any>(parcel)
        }

        override fun newArray(size: Int): Array<HttpResponse<*>?> {
            return arrayOfNulls(size)
        }
    }
}