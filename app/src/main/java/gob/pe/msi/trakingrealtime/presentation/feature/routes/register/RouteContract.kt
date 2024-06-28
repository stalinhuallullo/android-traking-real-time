package gob.pe.msi.trakingrealtime.presentation.feature.routes.register

import gob.pe.msi.trakingrealtime.data.model.HttpResponseRoutes
import gob.pe.msi.trakingrealtime.domain.entity.ResponseRoute

interface RouteContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showDataRoute(response: HttpResponseRoutes)
        fun showDataBus(response: ResponseRoute)
        fun showError(message: String)
    }

    interface Presenter {
        fun detachView()
        fun getDataRoute()
        fun getDataBuses(id: Int)
    }
}