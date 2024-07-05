package gob.pe.msi.trakingrealtime.presentation.feature.routes.register

import gob.pe.msi.trakingrealtime.data.model.HttpResponseBus
import gob.pe.msi.trakingrealtime.data.model.HttpResponseRoutes
import gob.pe.msi.trakingrealtime.domain.entity.ResponseRoute
import gob.pe.msi.trakingrealtime.domain.interactor.BaseObserver
import gob.pe.msi.trakingrealtime.domain.interactor.RouteUseCase

class RoutePresenter  (
    private var view: RouteContract.View,
    private val routeUseCase: RouteUseCase
) : RouteContract.Presenter {

    fun onStartNavigationClicked() {
        view.navigateToNavigationActivity()
    }

    override fun getDataRoute() {
        view.showLoading()
        routeUseCase.getListRoutes(UpdateRouteObserver())
    }

    private inner class UpdateRouteObserver : BaseObserver<HttpResponseRoutes>() {

        override fun onNext(t: HttpResponseRoutes) {
            view.hideLoading()
            view.showDataRoute(t)
            super.onNext(t)

        }

        override fun onComplete() {
            super.onComplete()
        }

        override fun onError(exception: Throwable) {
            //routeView!!.onError(ErrorFactory.create(exception))
            view!!.hideLoading()
            super.onError(exception)
        }
    }

    override fun getDataBuses(code: String) {
        view.showLoading()
        routeUseCase.getListBuses(code, UpdateBusesObserver())
    }

    private inner class UpdateBusesObserver : BaseObserver<HttpResponseBus>() {

        override fun onNext(t: HttpResponseBus) {
            view.hideLoading()
            view.showDataBus(t)
            super.onNext(t)

        }

        override fun onComplete() {
            super.onComplete()
        }

        override fun onError(exception: Throwable) {
            //routeView!!.onError(ErrorFactory.create(exception))
            view.hideLoading()
            super.onError(exception)
        }
    }

    override fun detachView() {
        routeUseCase.dispose()

    }


}