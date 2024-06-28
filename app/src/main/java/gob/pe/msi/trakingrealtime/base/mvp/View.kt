package gob.pe.msi.trakingrealtime.base.mvp

import android.content.Context

interface View {
    /**
     * Get a [Context].
     */
    fun context(): Context?
}