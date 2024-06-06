package gob.pe.msi.trakingrealtime.presentation.feature.traking.model.dto

data class GPSExpresoResponseDto(
    val CodigoRespuesta: String,
    val Respuesta: String,
) {

    class Builder {
        private var CodigoRespuesta: String? = null
        private var Respuesta: String? = null

        fun CodigoRespuesta(codigoRespuesta: String) = apply { this.CodigoRespuesta = codigoRespuesta }
        fun Respuesta(respuesta: String?) = apply { this.Respuesta = respuesta }

        fun build() = GPSExpresoResponseDto(CodigoRespuesta!!, Respuesta!!)
    }
}
