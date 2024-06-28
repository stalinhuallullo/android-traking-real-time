package gob.pe.msi.trakingrealtime.domain.interactor

import dagger.internal.Preconditions
import gob.pe.msi.trakingrealtime.domain.executor.PostExecutionThread
import gob.pe.msi.trakingrealtime.domain.executor.ThreadExecutor
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

abstract class UseCase internal constructor(
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread
) {
    private val disposables: CompositeDisposable = CompositeDisposable()

    /**
     * Ejecuta el actual caso de uso
     *
     * @param observable [Observable]
     * @param observer   [DisposableObserver] which will be listening to the observable build
     * by observable method.
     */
    fun <T : Any> execute(observable: Observable<T>, observer: DisposableObserver<T>) {
        println("execute ========")
        Preconditions.checkNotNull(observer)
        val disposable = observable
            .subscribeOn(Schedulers.from(threadExecutor))
            .observeOn(postExecutionThread.scheduler!!)
            .subscribeWith(observer)
        addDisposable(disposable)
    }

    /**
     * Finaliza el actual [CompositeDisposable].
     */
    fun dispose() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }


    /**
     * Agrega el [CompositeDisposable].
     */
    private fun addDisposable(disposable: Disposable) {
        Preconditions.checkNotNull(disposable)
        Preconditions.checkNotNull(disposables)
        disposables.add(disposable)
    }
}