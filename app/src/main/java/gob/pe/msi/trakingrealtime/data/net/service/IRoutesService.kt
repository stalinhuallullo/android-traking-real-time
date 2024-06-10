package gob.pe.msi.trakingrealtime.data.net.service

import io.reactivex.rxjava3.core.Observable
import gob.pe.msi.trakingrealtime.data.entity.RouteListResponseEntity
import gob.pe.msi.trakingrealtime.data.model.HttpResponse
import gob.pe.msi.trakingrealtime.data.model.HttpResponseRoutes
import gob.pe.msi.trakingrealtime.presentation.feature.traking.model.dto.GPSExpresoDto
import gob.pe.msi.trakingrealtime.presentation.feature.traking.model.dto.GPSExpresoResponseDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface IRoutesService {
    @GET("/EXPRESOBUS/BusLinea")
    fun listRoutes(@HeaderMap headers: Map<String, String>): Observable<HttpResponseRoutes>
}