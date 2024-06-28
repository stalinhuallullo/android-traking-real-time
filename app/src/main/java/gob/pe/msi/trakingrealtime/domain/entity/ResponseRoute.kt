package gob.pe.msi.trakingrealtime.domain.entity

import gob.pe.msi.trakingrealtime.data.entity.RouteListResponseEntity
import java.util.ArrayList

class ResponseRoute  {
    var CodigoRespuesta: String? = null
    var Respuesta: String? = null
    var Datos: ArrayList<RouteListResponseEntity>? = null
}