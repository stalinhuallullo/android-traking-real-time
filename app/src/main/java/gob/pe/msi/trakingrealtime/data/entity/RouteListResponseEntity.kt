package gob.pe.msi.trakingrealtime.data.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class RouteListResponseEntity(
    val CODLINEA: String,
    val TXTLINEA: String,
    val TXTRGBBUS: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(CODLINEA)
        parcel.writeString(TXTLINEA)
        parcel.writeString(TXTRGBBUS)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<RouteListResponseEntity> {
        override fun createFromParcel(parcel: Parcel): RouteListResponseEntity {
            return RouteListResponseEntity(parcel)
        }

        override fun newArray(size: Int): Array<RouteListResponseEntity?> {
            return arrayOfNulls(size)
        }
    }
}
