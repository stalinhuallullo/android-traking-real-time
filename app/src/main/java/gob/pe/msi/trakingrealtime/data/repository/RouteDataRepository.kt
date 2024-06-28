package gob.pe.msi.trakingrealtime.data.repository

import gob.pe.msi.trakingrealtime.data.mapper.RouteEntityDataMapper
import gob.pe.msi.trakingrealtime.data.repository.datasource.route.RouteDataStoreFactory
import gob.pe.msi.trakingrealtime.domain.entity.ResponseRoute
import gob.pe.msi.trakingrealtime.domain.repository.RouteDomainRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Singleton

@Singleton
class RouteDataRepository internal constructor (

) : RouteDomainRepository {

    private val routeDataStoreFactory: RouteDataStoreFactory = RouteDataStoreFactory()
    private val mapper = RouteEntityDataMapper()

    override fun getListRoutes(): Observable<ResponseRoute> {
        println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb")
        val dataStoreCloud = routeDataStoreFactory.createCloud()
        return dataStoreCloud.getListRoutes().map(mapper::transformHttpResponseRoutesToModel)
        //return routeDataStoreFactory.createCloud()
    }

}