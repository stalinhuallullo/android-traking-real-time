package gob.pe.msi.trakingrealtime.presentation.feature.routes.routerList

import gob.pe.msi.trakingrealtime.presentation.feature.routes.routerList.model.Route

interface RouteListView {
    fun onPharmacyClick(pharmacy: Route)
}