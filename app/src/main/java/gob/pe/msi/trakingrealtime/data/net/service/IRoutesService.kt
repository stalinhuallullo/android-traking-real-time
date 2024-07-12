package gob.pe.msi.trakingrealtime.data.net.service

import io.reactivex.rxjava3.core.Observable
import gob.pe.msi.trakingrealtime.data.model.HttpResponseRoutes
import gob.pe.msi.trakingrealtime.domain.entity.RouteWhereabout
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Path

interface IRoutesService {
    @GET("/EXPRESOBUS/BusLinea")
    fun getListRoutes(): Observable<HttpResponseRoutes>

    @GET("api/whereabout/{codRoute}")
    fun getRouteWhereabout(@Path("codRoute") codRoute: String): Observable<RouteWhereabout>
}