package gob.pe.msi.trakingrealtime.presentation.feature.navegation

import com.mapbox.geojson.Point

interface NavegationContract {
    interface View {
        fun showLocationSentSuccess()
        fun showLocationSentError()
    }

    interface Presenter {
        fun onLocationChanged(location: Point)
        fun onDestroy()
    }
}