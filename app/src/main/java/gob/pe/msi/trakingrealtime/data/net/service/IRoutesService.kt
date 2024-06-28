package gob.pe.msi.trakingrealtime.data.net.service

import io.reactivex.rxjava3.core.Observable
import gob.pe.msi.trakingrealtime.data.model.HttpResponseRoutes
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface IRoutesService {
    @GET("/EXPRESOBUS/BusLinea")
    fun listRoutes(): Observable<HttpResponseRoutes>
}