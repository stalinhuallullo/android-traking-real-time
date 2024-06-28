package gob.pe.msi.trakingrealtime.data.repository.datasource.route

import android.content.Context
import gob.pe.msi.trakingrealtime.data.net.service.IRoutesService
import gob.pe.msi.trakingrealtime.data.net.service.impl.RoutesService
import gob.pe.msi.trakingrealtime.utils.Constants
import javax.inject.Singleton

@Singleton
class RouteDataStoreFactory {


    /**
     * Metodo que crea la clase que obtendra los datos desde el Servicio
     *
     * @return class Clase que se encargara de obtener los datos desde el Servicio
     */
    fun createCloud(): RouteDataStore {
        println("createCloud =========  ${Constants.BASE_URL_REMOTO}")
        val service: IRoutesService = RoutesService(Constants.BASE_URL_REMOTO)
        return RouteCloudDataStore(service)
    }
}