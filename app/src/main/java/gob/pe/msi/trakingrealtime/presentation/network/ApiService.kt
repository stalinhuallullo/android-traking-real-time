package gob.pe.msi.trakingrealtime.presentation.network

import gob.pe.msi.trakingrealtime.presentation.model.dto.LocationDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST


interface ApiService {
    //@GET("users")
    //Call<List<User>> getUsers();

    @POST("/api/v1/traking")
    fun sendLocation(@HeaderMap headers: Map<String, String>, @Body location: LocationDto): Call<LocationDto?>?
}
