package gob.pe.msi.trakingrealtime.data.model

data class HttpResponse<T> (
    val CodigoRespuesta: String,
    val Respuesta: String,
    val Datos: Any?
) {
    val datosList: List<T>?
        get() = if (Datos is List<*>) {
            @Suppress("UNCHECKED_CAST")
            Datos as? List<T>
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
}
