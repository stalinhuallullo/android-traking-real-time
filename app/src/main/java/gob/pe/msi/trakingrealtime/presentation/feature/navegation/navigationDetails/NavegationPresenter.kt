package gob.pe.msi.trakingrealtime.presentation.feature.navegation.navigationDetails

import com.mapbox.geojson.Point
import gob.pe.msi.trakingrealtime.domain.entity.RouteWhereabout
import gob.pe.msi.trakingrealtime.domain.interactor.BaseObserver
import gob.pe.msi.trakingrealtime.domain.interactor.RouteUseCase

class NavegationPresenter(
    private val view: NavegationContract.View,
    private val case: RouteUseCase
) : NavegationContract.Presenter {
    override fun getRouteWhereabout(codRoute: String) {
        view.showLoading()
        case.getRouteWhereabout(codRoute, GetDataRoutes())
    }

    private inner class GetDataRoutes() : BaseObserver<RouteWhereabout>() {
        override fun onNext(t: RouteWhereabout) {
            view.hideLoading()
            println("onNext =====> $t")
            view.showDataRouteWhereabout(t)
            super.onNext(t)
        }

        override fun onComplete() {
            super.onComplete()
        }

        override fun onError(exception: Throwable) {
            view.hideLoading()
            println("Error NavegationPresenter $exception")
            super.onError(exception)
        }

    }

    override fun detachView() {
        case.dispose()

    }

}