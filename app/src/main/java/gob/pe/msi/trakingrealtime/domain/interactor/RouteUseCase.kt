package gob.pe.msi.trakingrealtime.domain.interactor

import gob.pe.msi.trakingrealtime.data.model.HttpResponseBus
import gob.pe.msi.trakingrealtime.data.model.HttpResponseRoutes
import gob.pe.msi.trakingrealtime.data.repository.BusRepository
import gob.pe.msi.trakingrealtime.data.repository.RouteDataRepository
import gob.pe.msi.trakingrealtime.data.repository.RouteRepository
import gob.pe.msi.trakingrealtime.domain.entity.ResponseRoute
import gob.pe.msi.trakingrealtime.domain.entity.RouteWhereabout
import gob.pe.msi.trakingrealtime.domain.exception.ExpiredSessionException
import gob.pe.msi.trakingrealtime.domain.executor.PostExecutionThread
import gob.pe.msi.trakingrealtime.domain.executor.ThreadExecutor
import gob.pe.msi.trakingrealtime.domain.repository.RouteDomainRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.functions.Function
import javax.inject.Inject


class RouteUseCase
/**
 * Constructor del caso de uso
 *
 * @param threadExecutor      Ejecutor de un metodo
 * @param postExecutionThread Tipo de ejecucion en un hilo diferente
 */
(
    private val routeRepository: RouteRepository,
    private val busRepository: BusRepository,
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread
): UseCase(threadExecutor, postExecutionThread) {

    fun getListRoutes(observer: DisposableObserver<HttpResponseRoutes>) {
        execute(routeRepository.getListRoutes(), observer)
    }

    fun getListBuses(code: String, observer: DisposableObserver<HttpResponseBus>) {
        execute(busRepository.getListBuses(code), observer)
    }

    fun getRouteWhereabout(codRoute: String, observer: DisposableObserver<RouteWhereabout>) {
        execute(routeRepository.getRouteWhereabout(codRoute), observer)
    }
}