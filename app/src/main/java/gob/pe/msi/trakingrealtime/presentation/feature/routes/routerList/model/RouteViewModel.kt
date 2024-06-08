package gob.pe.msi.trakingrealtime.presentation.feature.routes.routerList.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gob.pe.msi.trakingrealtime.presentation.feature.routes.routerList.model.dto.RouteDto

class RouteViewModel : ViewModel() {
    private val _pharmacies = MutableLiveData<List<RouteDto>>()
    val pharmacies: LiveData<List<RouteDto>> get() = _pharmacies

    fun loadPharmacies() {
        // Simula la carga de datos
        _pharmacies.value = listOf(
            RouteDto("PERIFERICA", "RUTA 1","Av. La Fontana Nro. 466 Int. 1032 Urb. San Cesar Ii Etapa"),
            RouteDto("NORTE CENTRO FINANCIERO","RUTA 1", "Av. La Molina Nro. 820 Int. 1 Urb. Ampliacion Residencial Monterrico"),
            RouteDto("SUR CENTRO FINANCIERO", "RUTA 1","Av. Flora Tristan 691 Mz. O Lt. 3 Esq. Calle Piura N° 106-108 Tda. 1,2,3 Urb. Santa Patricia")
        )
    }
}