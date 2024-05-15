package gob.pe.msi.trakingrealtime.presentation.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gob.pe.msi.trakingrealtime.presentation.model.dto.LocationDto
import gob.pe.msi.trakingrealtime.presentation.network.ApiClient
import gob.pe.msi.trakingrealtime.presentation.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class TrakingRepository {
    private val apiService: ApiService = ApiClient.retrofitInstance!!.create(ApiService::class.java)


    fun saveLocation (headers: Map<String, String>, location: LocationDto): LiveData<LocationDto> {
        val data = MutableLiveData<LocationDto>()
        apiService.sendLocation(headers, location)!!.enqueue(object : Callback<LocationDto?> {
            override fun onResponse(call: Call<LocationDto?>, response: Response<LocationDto?>) {
                if (response.isSuccessful && response.body() != null) {
                    data.value = response.body()
                } else {
                    Log.e("UserRepository", "Response Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LocationDto?>, t: Throwable) {
                Log.e("UserRepository", "Network Error: ${t.message}")
            }
        })
        return data
    }

    fun updateLocation() {
        Log.e("UserRepository", "Network Error}")
    }

}
