package gob.pe.msi.trakingrealtime.presentation.model.dto

data class GPSExpresoDto(
    val CodLinea: String,
    val Latitud: String,
    val Longitud: String,
) {

    class Builder {
        private var CodLinea: String? = null
        private var Latitud: String? = null
        private var Longitud: String? = null

        fun CodLinea(id: String) = apply { this.CodLinea = id }
        fun Latitud(latitude: String?) = apply { this.Latitud = latitude }
        fun Longitud(longitude: String?) = apply { this.Longitud = longitude }

        fun build() = GPSExpresoDto(CodLinea!!, Latitud!!, Longitud!!)
    }
}
