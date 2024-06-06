package gob.pe.msi.trakingrealtime.presentation.feature.traking.model.dto

data class LocationDto(
    val id: Int,
    val idUser: Int,
    val latitude: String?,
    val longitude: String?,
    val registered: String?
) {

    class Builder {
        private var id: Int = 0
        private var idUser: Int = 0
        private var latitude: String? = null
        private var longitude: String? = null
        private var registered: String? = null

        fun id(id: Int) = apply { this.id = id }
        fun idUser(idUser: Int) = apply { this.idUser = idUser }
        fun latitude(latitude: String?) = apply { this.latitude = latitude }
        fun longitude(longitude: String?) = apply { this.longitude = longitude }
        fun registered(registered: String?) = apply { this.registered = registered }

        fun build() = LocationDto(id, idUser, latitude, longitude, registered)
    }
}
