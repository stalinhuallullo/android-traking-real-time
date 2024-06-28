package gob.pe.msi.trakingrealtime.data.repository.datasource.route

import gob.pe.msi.trakingrealtime.data.model.HttpResponseRoutes
import io.reactivex.rxjava3.core.Observable

interface RouteDataStore {
    fun getListRoutes(): Observable<HttpResponseRoutes>
}