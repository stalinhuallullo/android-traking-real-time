package gob.pe.msi.trakingrealtime.data.model

import android.os.Parcel
import android.os.Parcelable

data class BusDataModel(
    var CODBUS: String,
    var TXTBUS: String,
    var TXTPLACA: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(CODBUS)
        parcel.writeString(TXTBUS)
        parcel.writeString(TXTPLACA)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BusDataModel> {
        override fun createFromParcel(parcel: Parcel): BusDataModel {
            return BusDataModel(parcel)
        }

        override fun newArray(size: Int): Array<BusDataModel?> {
            return arrayOfNulls(size)
        }
    }


}