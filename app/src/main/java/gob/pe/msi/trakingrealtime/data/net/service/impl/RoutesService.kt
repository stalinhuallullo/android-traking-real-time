package gob.pe.msi.trakingrealtime.data.net.service.impl

import gob.pe.msi.trakingrealtime.data.model.HttpResponseRoutes
import gob.pe.msi.trakingrealtime.data.net.RestApi
import gob.pe.msi.trakingrealtime.data.net.service.IRoutesService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.schedulers.Schedulers

class RoutesService: IRoutesService {

    private var apiService: IRoutesService = RestApi.retrofitInstance!!.create(IRoutesService::class.java)
    //private lateinit var apiService: IRoutesService

    constructor(){}
    constructor(url: String){
        println("==== GENERO CONSTRUCTOR ====")
        apiService = RestApi.createRetrofitInstance(url)!!.create(IRoutesService::class.java)
    }

    override fun listRoutes(): Observable<HttpResponseRoutes> {
        return Observable.create(ObservableOnSubscribe { emitter: ObservableEmitter<HttpResponseRoutes> ->
            apiService.listRoutes()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        value: HttpResponseRoutes -> emitter.onNext(value)
                    },
                    {
                        error ->
                            if (!emitter.isDisposed) {
                                emitter.onError(error)
                            }
                    },
                    {
                        emitter.onComplete()
                    }
                )
        })

        /*val disposable = apiService.listRoutes()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                { value: HttpResponseRoutes ->
                    emitter.onNext(value!!)
                },
                { error ->
                    if (!emitter.isDisposed) {
                        emitter.onError(getError(error))
                    }
                }, { emitter.onComplete() }
            })*/

    }

    /*override fun listRoutes(headers: Map<String, String>): Call<HttpResponse<List<RouteListResponseEntity>>> {
        val data = MutableLiveData<HttpResponse<List<RouteListResponseEntity>>>()

        apiService.listRoutes(headers).enqueue( object : Callback<HttpResponse<List<RouteListResponseEntity>>> {
            override fun onResponse(
                call: Call<HttpResponse<List<RouteListResponseEntity>>>,
                response: Response<HttpResponse<List<RouteListResponseEntity>>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    data.value = response.body()
                } else {
                    Log.e("RoutesService", "Response Error: ${response.message()}")
                }
            }

            override fun onFailure(
                call: Call<HttpResponse<List<RouteListResponseEntity>>>,
                t: Throwable
            ) {
                Log.e("RoutesService", "Network Error: ${t.message}")
            }

        })


        return data
    }*/


}