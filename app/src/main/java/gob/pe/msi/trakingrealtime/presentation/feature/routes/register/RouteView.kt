package gob.pe.msi.trakingrealtime.presentation.feature.routes.register

import gob.pe.msi.trakingrealtime.base.mvp.LoadingView
import gob.pe.msi.trakingrealtime.base.mvp.View

interface RouteView : LoadingView, View {

    fun setPackRecurrency()
    fun onError(errorModel: String)

}