package gob.pe.msi.trakingrealtime.domain.executor

import io.reactivex.rxjava3.core.Scheduler

/**
 * Interface que implementa la ejecucion de un proceso en un hilo diferente al principal
 */
interface PostExecutionThread {
    val scheduler: Scheduler?
}