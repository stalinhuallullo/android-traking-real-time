package gob.pe.msi.trakingrealtime.presentation.feature.routes.model

data class ResponseModel(
    val CodigoRespuesta: String,
    val Respuesta: String,
    val Datos: List<Dato>
) {
    data class Dato(
        val CODLINEA: String,
        val TXTLINEA: String,
        val TXTRGBBUS: String
    )
}