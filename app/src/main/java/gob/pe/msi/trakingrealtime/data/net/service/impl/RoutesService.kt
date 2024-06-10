package gob.pe.msi.trakingrealtime.data.net.service.impl

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gob.pe.msi.trakingrealtime.data.entity.RouteListResponseEntity
import gob.pe.msi.trakingrealtime.data.model.HttpResponse
import gob.pe.msi.trakingrealtime.data.model.HttpResponseRoutes
import gob.pe.msi.trakingrealtime.data.net.RestApi
import gob.pe.msi.trakingrealtime.data.net.service.BaseService
import gob.pe.msi.trakingrealtime.data.net.service.IRoutesService
import gob.pe.msi.trakingrealtime.data.net.service.ITrackingService
import gob.pe.msi.trakingrealtime.presentation.feature.traking.model.dto.GPSExpresoDto
import gob.pe.msi.trakingrealtime.presentation.feature.traking.model.dto.GPSExpresoResponseDto
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoutesService: IRoutesService{

    private val apiService: IRoutesService = RestApi.retrofitInstance!!.create(IRoutesService::class.java)


    override fun listRoutes(headers: Map<String, String>): Observable<HttpResponseRoutes> {
        return apiService.listRoutes(headers)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    /*override fun listRoutes(headers: Map<String, String>): Call<HttpResponse<List<RouteListResponseEntity>>> {
        val data = MutableLiveData<HttpResponse<List<RouteListResponseEntity>>>()

        apiService.listRoutes(headers).enqueue( object : Callback<HttpResponse<List<RouteListResponseEntity>>> {
            override fun onResponse(
                call: Call<HttpResponse<List<RouteListResponseEntity>>>,
                response: Response<HttpResponse<List<RouteListResponseEntity>>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    data.value = response.body()
                } else {
                    Log.e("RoutesService", "Response Error: ${response.message()}")
                }
            }

            override fun onFailure(
                call: Call<HttpResponse<List<RouteListResponseEntity>>>,
                t: Throwable
            ) {
                Log.e("RoutesService", "Network Error: ${t.message}")
            }

        })


        return data
    }*/


}