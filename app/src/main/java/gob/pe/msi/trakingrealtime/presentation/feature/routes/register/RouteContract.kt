package gob.pe.msi.trakingrealtime.presentation.feature.routes.register

import androidx.annotation.NonNull
import gob.pe.msi.trakingrealtime.data.model.HttpResponseBus
import gob.pe.msi.trakingrealtime.data.model.HttpResponseRoutes
import gob.pe.msi.trakingrealtime.domain.entity.ResponseRoute

interface RouteContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showDataRoute(response: HttpResponseRoutes)
        fun showDataBus(response: HttpResponseBus)
        fun showError(message: String)
        fun navigateToNavigationActivity()
    }

    interface Presenter {
        fun detachView()
        fun getDataRoute()
        fun getDataBuses(id: String)
    }
}