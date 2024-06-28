package gob.pe.msi.trakingrealtime.presentation.feature.routes.register

import gob.pe.msi.trakingrealtime.data.model.HttpResponseRoutes
import gob.pe.msi.trakingrealtime.domain.entity.ResponseRoute
import gob.pe.msi.trakingrealtime.domain.interactor.BaseObserver
import gob.pe.msi.trakingrealtime.domain.interactor.RouteUseCase

class RoutePresenter  (
    private val view: RouteContract.View,
    private val routeUseCase: RouteUseCase
) : RouteContract.Presenter {

    override fun getDataRoute() {
        view.showLoading()
        routeUseCase.getListRoutes(UpdateRouteObserver())
    }

    private inner class UpdateRouteObserver : BaseObserver<HttpResponseRoutes>() {

        override fun onNext(t: HttpResponseRoutes) {
            println("TAYLOR onNext 1=========> $t")
            println("TAYLOR onNext 2=========> ${t.CodigoRespuesta}")
            println("TAYLOR onNext 3=========> ${t.Respuesta}")
            println("TAYLOR onNext 4=========> ${t.Datos}")
            view.hideLoading()
            view.showDataRoute(t)
            super.onNext(t)

        }

        override fun onComplete() {
            println("TAYLOR onComplete =======> ")
            super.onComplete()
        }

        override fun onError(exception: Throwable) {
            println("TAYLOR onError =======> $exception")
            //routeView!!.onError(ErrorFactory.create(exception))
            view!!.hideLoading()
            super.onError(exception)
        }
    }

    override fun getDataBuses(id: Int) {
        view.showLoading()
        routeUseCase.getListBuses(UpdateBusesObserver())
    }
    private inner class UpdateBusesObserver : BaseObserver<ResponseRoute>() {

        override fun onNext(t: ResponseRoute) {
            println("TAYLOR showDataBus 1=========> $t")
            println("TAYLOR showDataBus 2=========> ${t.CodigoRespuesta}")
            println("TAYLOR showDataBus 3=========> ${t.Respuesta}")
            println("TAYLOR showDataBus 4=========> ${t.Datos}")
            view.hideLoading()
            view.showDataBus(t)
            super.onNext(t)

        }

        override fun onComplete() {
            println("TAYLOR onComplete =======> ")
            super.onComplete()
        }

        override fun onError(exception: Throwable) {
            println("TAYLOR onError =======> $exception")
            //routeView!!.onError(ErrorFactory.create(exception))
            view!!.hideLoading()
            super.onError(exception)
        }
    }

    override fun detachView() {
        routeUseCase.dispose()
    }


}