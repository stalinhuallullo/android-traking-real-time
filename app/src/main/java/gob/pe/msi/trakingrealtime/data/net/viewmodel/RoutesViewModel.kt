package gob.pe.msi.trakingrealtime.data.net.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gob.pe.msi.trakingrealtime.data.entity.RouteListResponseEntity
import gob.pe.msi.trakingrealtime.data.model.HttpResponse
import gob.pe.msi.trakingrealtime.data.model.HttpResponseRoutes
import gob.pe.msi.trakingrealtime.data.net.service.impl.RoutesService

class RoutesViewModel : ViewModel() {
    private val routesService = RoutesService()
    private val _routesLiveData = MutableLiveData<HttpResponseRoutes>()
    val routesLiveData: LiveData<HttpResponseRoutes> = _routesLiveData

    fun fetchRoutes(headers: Map<String, String>) {
        routesService.listRoutes(headers)
            .subscribe({ response ->
                _routesLiveData.value = response
            }, { error ->
                _routesLiveData.value = HttpResponseRoutes(
                    CodigoRespuesta = "99",
                    Respuesta = "Network Error: ${error.message}",
                    Datos = null
                )
            })
    }
}