package gob.pe.msi.trakingrealtime.data.net.service.impl

import gob.pe.msi.trakingrealtime.data.model.HttpResponseBus
import gob.pe.msi.trakingrealtime.data.net.RestApi
import gob.pe.msi.trakingrealtime.data.net.service.IBusesService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers


class BusesService: IBusesService {

    private val apiService: IBusesService = RestApi.retrofitInstance!!.create(IBusesService::class.java)

    override fun listBuses(code: String): Observable<HttpResponseBus> {
        return  apiService.listBuses(code)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}