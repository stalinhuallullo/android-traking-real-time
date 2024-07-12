package gob.pe.msi.trakingrealtime.domain.entity

import com.google.gson.annotations.SerializedName

class RouteWhereabout () {
    @SerializedName("route")
    var route: Route? = null
    @SerializedName("whereabout")
    var whereabout: List<Whereabout>? = null
}