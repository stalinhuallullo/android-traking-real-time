package gob.pe.msi.trakingrealtime.presentation.feature.traking.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gob.pe.msi.trakingrealtime.presentation.feature.traking.model.dto.GPSExpresoDto
import gob.pe.msi.trakingrealtime.presentation.feature.traking.model.dto.GPSExpresoResponseDto
import gob.pe.msi.trakingrealtime.presentation.feature.traking.model.dto.LocationDto
import gob.pe.msi.trakingrealtime.data.net.RestApi
import gob.pe.msi.trakingrealtime.data.net.service.ITrackingService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrakingRepository {
    private val apiService: ITrackingService = RestApi.retrofitInstance!!.create(ITrackingService::class.java)


    fun saveLocation (headers: Map<String, String>, location: LocationDto): LiveData<GPSExpresoResponseDto> {
        val data = MutableLiveData<GPSExpresoResponseDto>()

        val newLocation = GPSExpresoDto
            .Builder()
            .CodLinea("01")
            .Latitud(location.latitude)
            .Longitud(location.longitude)
            .build()

        apiService.sendLocation(headers, newLocation)!!.enqueue(object : Callback<GPSExpresoResponseDto?> {
            override fun onResponse(call: Call<GPSExpresoResponseDto?>, response: Response<GPSExpresoResponseDto?>) {
                if (response.isSuccessful && response.body() != null) {
                    data.value = response.body()
                } else {
                    Log.e("UserRepository", "Response Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GPSExpresoResponseDto?>, t: Throwable) {
                Log.e("UserRepository", "Network Error: ${t.message}")
            }
        })
        return data
    }

    fun updateLocation() {
        Log.e("UserRepository", "Network Error}")
    }

}
