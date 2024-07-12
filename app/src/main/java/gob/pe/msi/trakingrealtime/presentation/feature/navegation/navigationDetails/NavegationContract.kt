package gob.pe.msi.trakingrealtime.presentation.feature.navegation.navigationDetails

import com.mapbox.geojson.Point
import gob.pe.msi.trakingrealtime.data.model.HttpResponseBus
import gob.pe.msi.trakingrealtime.data.model.HttpResponseRoutes
import gob.pe.msi.trakingrealtime.domain.entity.RouteWhereabout

interface NavegationContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showDataRouteWhereabout(response: RouteWhereabout)
        fun showError(message: String)
    }

    interface Presenter {
        fun detachView()
        fun getRouteWhereabout(codRoute: String)
    }
}