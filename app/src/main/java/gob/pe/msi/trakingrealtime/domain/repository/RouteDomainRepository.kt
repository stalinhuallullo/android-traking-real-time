package gob.pe.msi.trakingrealtime.domain.repository

import gob.pe.msi.trakingrealtime.domain.entity.ResponseRoute
import io.reactivex.rxjava3.core.Observable

interface RouteDomainRepository {

    fun getListRoutes(): Observable<ResponseRoute>
}