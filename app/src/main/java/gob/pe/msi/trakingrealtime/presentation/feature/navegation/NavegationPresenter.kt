package gob.pe.msi.trakingrealtime.presentation.feature.navegation

import com.mapbox.geojson.Point

class NavegationPresenter(
    private val view: NavegationContract.View,
    //private val sendLocationUseCase: SendLocationUseCase
) : NavegationContract.Presenter {

    //private val compositeDisposable = CompositeDisposable()

    override fun onLocationChanged(location: Point) {
        println("Point =======> $location")
        /*val disposable = sendLocationUseCase.execute(location)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { view.showLocationSentSuccess() },
                { view.showLocationSentError() }
            )
        compositeDisposable.add(disposable)*/
    }

    override fun onDestroy() {
        //compositeDisposable.clear()
    }
}