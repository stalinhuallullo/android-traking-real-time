package gob.pe.msi.trakingrealtime.data.net.service.impl

import gob.pe.msi.trakingrealtime.data.model.HttpResponseBus
import gob.pe.msi.trakingrealtime.data.model.HttpResponseRoutes
import gob.pe.msi.trakingrealtime.data.net.RestApi
import gob.pe.msi.trakingrealtime.data.net.service.IBusesService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.schedulers.Schedulers


class BusesService (): IBusesService {

    private var apiService: IBusesService = RestApi.retrofitInstance!!.create(IBusesService::class.java)

    override fun listBuses(code: String): Observable<HttpResponseBus> {
        return Observable.create(ObservableOnSubscribe {emitter: ObservableEmitter<HttpResponseBus> ->
            apiService.listBuses(code)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        value: HttpResponseBus -> emitter.onNext(value)
                    },
                    {
                        error -> if (!emitter.isDisposed) {
                            emitter.onError(error)
                        }
                    },
                    {
                        emitter.onComplete()
                    }
                )
        })
    }

}