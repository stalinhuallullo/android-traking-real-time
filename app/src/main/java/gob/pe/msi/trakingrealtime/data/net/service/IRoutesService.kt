package gob.pe.msi.trakingrealtime.data.net.service

import io.reactivex.rxjava3.core.Observable
import gob.pe.msi.trakingrealtime.data.entity.RouteListResponseEntity
import gob.pe.msi.trakingrealtime.data.model.HttpResponse
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface IRoutesService {
    @POST("/EXPRESOBUS/BusLinea")
    fun listRoutes(@HeaderMap headers: Map<String, String>): Observable<HttpResponse<List<RouteListResponseEntity>>>

}