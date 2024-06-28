package gob.pe.msi.trakingrealtime.data.mapper

import gob.pe.msi.trakingrealtime.data.model.HttpResponseRoutes
import gob.pe.msi.trakingrealtime.domain.entity.ResponseRoute
import javax.inject.Singleton

@Singleton
class RouteEntityDataMapper internal constructor() {

    fun transformHttpResponseRoutesToModel(input: HttpResponseRoutes): ResponseRoute {
        //if (input == null) return null
        val output = ResponseRoute()
        output.CodigoRespuesta = input.CodigoRespuesta
        output.Respuesta = input.Respuesta
        output.Datos = input.Datos

        return output;
    }

    /*fun transformHttpResponseRoutesToModel(input: HttpResponseRoutes): List<RouteList> {

        val output: MutableList<RouteList> = ArrayList()
        if (input == null) {
            return output;
        }
        input.Datos!!.forEach { item ->
            output.add(transformRecurrencyEntityToModel(item))
        }
        return output;
    }
    private fun transformRecurrencyEntityToModel(input: RouteListResponseEntity): RouteList {
        val output = RouteList()
        output.codLinea = input.CODLINEA
        output.linea = input.TXTLINEA
        output.rgbBus = input.TXTRGBBUS

        return output
    }*/
}