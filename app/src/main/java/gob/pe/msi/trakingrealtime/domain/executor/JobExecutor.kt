package gob.pe.msi.trakingrealtime.domain.executor

import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class JobExecutor : ThreadExecutor {
    private val threadPoolExecutor: ThreadPoolExecutor

    init {
        val corePoolSize = 3
        val maximumPoolSize = 5
        val keepAliveTime = 10
        threadPoolExecutor = ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime.toLong(), TimeUnit.SECONDS, LinkedBlockingQueue())
    }

    override fun execute(runnable: Runnable) {
        threadPoolExecutor.execute(runnable)
    }
}