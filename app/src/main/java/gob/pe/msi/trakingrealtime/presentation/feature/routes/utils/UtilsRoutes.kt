package gob.pe.msi.trakingrealtime.presentation.feature.routes.utils

import gob.pe.msi.trakingrealtime.R

object UtilsRoutes {

    fun changeImageByText(route: String?): Int {
        if(route==null) return R.drawable.coverage_area

        if (route.indexOf("1", 0) >= 0) return R.drawable.ic_location_blue
        if (route.indexOf("2", 0) >= 0) return R.drawable.map_arrow_down_grey
        if (route.indexOf("3", 0) >= 0) return R.drawable.ic_route_24
        //if(address.indexOf("otros", 0) >= 0) return R.drawable.ic_location_recurrency
        else return R.drawable.coverage_area
    }
}