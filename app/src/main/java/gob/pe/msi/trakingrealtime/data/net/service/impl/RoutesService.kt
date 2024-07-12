package gob.pe.msi.trakingrealtime.data.net.service.impl

import gob.pe.msi.trakingrealtime.data.model.HttpResponseRoutes
import gob.pe.msi.trakingrealtime.data.net.RestApi
import gob.pe.msi.trakingrealtime.data.net.service.IRoutesService
import gob.pe.msi.trakingrealtime.domain.entity.RouteWhereabout
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.http.Path

class RoutesService: IRoutesService {

    private var apiService: IRoutesService = RestApi.retrofitInstance!!.create(IRoutesService::class.java)
    private var apiServiceLocal: IRoutesService = RestApi.retrofitInstanceLocal!!.create(IRoutesService::class.java)

    override fun getListRoutes(): Observable<HttpResponseRoutes> {
        return Observable.create(ObservableOnSubscribe { emitter: ObservableEmitter<HttpResponseRoutes> ->
            apiService.getListRoutes()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        value -> emitter.onNext(value)
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

    override fun getRouteWhereabout(@Path("codRoute") codRoute: String): Observable<RouteWhereabout> {
        return Observable.create(ObservableOnSubscribe { emitter: ObservableEmitter<RouteWhereabout> ->
            apiServiceLocal.getRouteWhereabout(codRoute)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        value -> emitter.onNext(value)
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