package gob.pe.msi.trakingrealtime.data.repository

import gob.pe.msi.trakingrealtime.data.model.HttpResponseBus
import gob.pe.msi.trakingrealtime.data.net.service.impl.BusesService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Singleton

@Singleton
class BusRepository (private val busesService: BusesService) {

    fun getListBuses(code: String): Observable<HttpResponseBus> {
        return  busesService.listBuses(code)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}