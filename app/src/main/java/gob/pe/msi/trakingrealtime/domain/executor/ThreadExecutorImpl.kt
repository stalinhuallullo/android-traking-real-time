package gob.pe.msi.trakingrealtime.domain.executor

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ThreadExecutorImpl : ThreadExecutor {

    private val executor: Executor = Executors.newFixedThreadPool(3) // Ejemplo de un ThreadPool fijo con 3 hilos

    override fun execute(command: Runnable?) {
        if (command != null) {
            executor.execute(command)
        }
    }
}