package gob.pe.msi.trakingrealtime.data.net.service.impl

import android.content.Context
import gob.pe.msi.trakingrealtime.data.entity.RouteListResponseEntity
import gob.pe.msi.trakingrealtime.data.model.HttpResponse
import gob.pe.msi.trakingrealtime.data.net.service.IRoutesService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.schedulers.Schedulers

class RoutesService: IRoutesService{

    private lateinit var service: IRoutesService

    /*constructor(context: Context?, token: String?) : super(context) {
        service = create(IProductService::class.java, token, drugstoreStock, drugstoreCode)
    }*/

    override fun listRoutes(headers: Map<String, String>): Observable<HttpResponse<List<RouteListResponseEntity>>> {
        return Observable.create {emitter: ObservableEmitter<HttpResponse<List<RouteListResponseEntity>>> ->
            //if (isThereInternetConnection) {
                try {
                    service.listRoutes(headers)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ value -> value?.let { emitter.onNext(it) } },
                            { error: Throwable? ->
                                if (!emitter.isDisposed) {
                                    println("error ===============> ${error}")
                                    //emitter.onError(getError(error))
                                }
                            }) { emitter.onComplete() }
                } catch (e: Exception) {
                    println("catch Exception  ===============> ${e.cause}")
                    //emitter.onError(ServiceException(e.cause))
                }
            /*} else if (!emitter.isDisposed) {
                emitter.onError(NetworkConnectionException())
            }*/
        }

    }

}