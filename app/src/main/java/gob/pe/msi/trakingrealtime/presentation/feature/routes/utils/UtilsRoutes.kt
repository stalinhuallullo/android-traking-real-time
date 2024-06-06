package gob.pe.msi.trakingrealtime.presentation.feature.routes.utils

import gob.pe.msi.trakingrealtime.R

object UtilsRoutes {

    fun changeImageByText(route: String?): Int {
        if(route==null) return R.drawable.coverage_area

        if (route.indexOf("01", 0) >= 0) return R.drawable.ic_location_blue
        if (route.indexOf("02", 0) >= 0) return R.drawable.icon_enable_location
        if (route.indexOf("03", 0) >= 0) return R.drawable.icon_enable_location
        //if(address.indexOf("otros", 0) >= 0) return R.drawable.ic_location_recurrency
        else return R.drawable.coverage_area
    }
}