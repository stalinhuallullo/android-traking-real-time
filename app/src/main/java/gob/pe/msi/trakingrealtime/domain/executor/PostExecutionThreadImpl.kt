package gob.pe.msi.trakingrealtime.domain.executor

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler

class PostExecutionThreadImpl : PostExecutionThread {

    override val scheduler: Scheduler?
        get() = AndroidSchedulers.mainThread()
}