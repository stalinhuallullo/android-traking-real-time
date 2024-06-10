package gob.pe.msi.trakingrealtime.data.net.service

import gob.pe.msi.trakingrealtime.presentation.feature.traking.model.dto.GPSExpresoDto
import gob.pe.msi.trakingrealtime.presentation.feature.traking.model.dto.GPSExpresoResponseDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST


interface ITrackingService {
    //@GET("users")
    //Call<List<User>> getUsers();

    // http://test.munisanisidro.gob.pe/EXPRESOBUS/InsertaGPSExpreso
    // /api/v1/traking

    @POST("/EXPRESOBUS/InsertaGPSExpreso")
    fun sendLocation(@HeaderMap headers: Map<String, String>, @Body location: GPSExpresoDto): Call<GPSExpresoResponseDto?>?
}
