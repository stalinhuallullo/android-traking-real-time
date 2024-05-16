package gob.pe.msi.trakingrealtime.presentation.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gob.pe.msi.trakingrealtime.presentation.model.dto.GPSExpresoDto
import gob.pe.msi.trakingrealtime.presentation.model.dto.GPSExpresoResponseDto
import gob.pe.msi.trakingrealtime.presentation.model.dto.LocationDto
import gob.pe.msi.trakingrealtime.presentation.network.ApiClient
import gob.pe.msi.trakingrealtime.presentation.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class TrakingRepository {
    private val apiService: ApiService = ApiClient.retrofitInstance!!.create(ApiService::class.java)


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
