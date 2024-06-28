package gob.pe.msi.trakingrealtime.data.repository

import dagger.internal.Preconditions
import gob.pe.msi.trakingrealtime.data.model.HttpResponseBus
import gob.pe.msi.trakingrealtime.data.model.HttpResponseRoutes
import gob.pe.msi.trakingrealtime.data.net.service.impl.BusesService
import gob.pe.msi.trakingrealtime.data.net.service.impl.RoutesService
import gob.pe.msi.trakingrealtime.domain.entity.ResponseRoute
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Singleton

@Singleton
class RouteRepository (private val routesService: RoutesService) {

    fun getListRoutes(): Observable<HttpResponseRoutes> {
        return routesService.listRoutes()
        /*return  routesService.listRoutes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())*/
    }



}