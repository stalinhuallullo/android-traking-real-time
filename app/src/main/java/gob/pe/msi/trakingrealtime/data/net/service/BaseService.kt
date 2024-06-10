package gob.pe.msi.trakingrealtime.data.net.service

import android.content.Context
import gob.pe.msi.trakingrealtime.librery.utils.NetworkUtil

/**
 * Clase base de un servicio
 * que contiene los metodos comunes que involucra cualquier servicio
 */
open class BaseService internal constructor(context: Context?) {
    protected val context: Context

    /**
     * Metodo que valida si tiene conexion a internet
     *
     * @return boolean Valor que determina la conectividad del dispositivo
     */
    protected val isThereInternetConnection: Boolean
        get() = NetworkUtil.isThereInternetConnection(context)


    /**
     * Constructor
     *
     * @param context Contexto que llamo al servicio
     */
    init {
        requireNotNull(context) { "El constructor no puede recibir parametros nulos!!!" }
        this.context = context
    }

    companion object {
        fun getError(error: Throwable?): Throwable {
            return Throwable()
            /*return try {
                val throwable: Throwable = parseError(error)
                if (throwable is UnauthorizedException) {
                    ExpiredSessionException()
                } else {
                    throwable
                }
            } catch (ex: Exception) {
                ServiceException(ex)
            }*/
        }
    }

}