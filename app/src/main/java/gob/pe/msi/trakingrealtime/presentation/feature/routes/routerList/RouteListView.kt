package gob.pe.msi.trakingrealtime.presentation.feature.routes.routerList

import gob.pe.msi.trakingrealtime.presentation.feature.routes.routerList.model.dto.RouteDto

interface RouteListView {
    fun onPharmacyClick(pharmacy: RouteDto)
}