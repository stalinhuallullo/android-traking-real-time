package gob.pe.msi.trakingrealtime.data.repository.datasource.route

import gob.pe.msi.trakingrealtime.data.model.HttpResponseRoutes
import gob.pe.msi.trakingrealtime.data.net.service.IRoutesService
import io.reactivex.rxjava3.core.Observable

class RouteCloudDataStore (private val service: IRoutesService) : RouteDataStore {
    override fun getListRoutes(): Observable<HttpResponseRoutes> {
        return service.listRoutes()
    }
}