package gob.pe.msi.trakingrealtime.data.net.service

import gob.pe.msi.trakingrealtime.data.model.HttpResponseBus
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface IBusesService {

    @GET("/EXPRESOBUS/BusRuta")
    fun listBuses(@Query("CODLINEA") code: String): Observable<HttpResponseBus>

}