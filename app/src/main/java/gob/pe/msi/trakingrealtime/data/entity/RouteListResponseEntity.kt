package gob.pe.msi.trakingrealtime.data.entity

import com.google.gson.annotations.SerializedName

class RouteListResponseEntity {

    @SerializedName("CODLINEA")
    var codLinea: String? = null

    @SerializedName("TXTLINEA")
    var txtLinea: String? = null

    @SerializedName("TXTRGBBUS")
    var txtRgbBus: String? = null
}
