package gob.pe.msi.trakingrealtime.base.mvp

interface LoadingView {
    /**
     * Muestra una vista con una barra de progreso que indica un proceso de carga.
     */
    fun showLoading()

    /**
     * Ocultar una vista de carga.
     */
    fun hideLoading()
}