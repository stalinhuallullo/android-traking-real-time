package gob.pe.msi.trakingrealtime.presentation.network

import gob.pe.msi.trakingrealtime.presentation.model.dto.GPSExpresoDto
import gob.pe.msi.trakingrealtime.presentation.model.dto.GPSExpresoResponseDto
import gob.pe.msi.trakingrealtime.presentation.model.dto.LocationDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST


interface ApiService {
    //@GET("users")
    //Call<List<User>> getUsers();

    // http://test.munisanisidro.gob.pe/EXPRESOBUS/InsertaGPSExpreso
    // /api/v1/traking

    @POST("/EXPRESOBUS/InsertaGPSExpreso")
    fun sendLocation(@HeaderMap headers: Map<String, String>, @Body location: GPSExpresoDto): Call<GPSExpresoResponseDto?>?
}
