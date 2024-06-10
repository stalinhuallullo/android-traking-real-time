package gob.pe.msi.trakingrealtime.librery.utils

import android.annotation.TargetApi
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import java.util.Objects

object NetworkUtil {
    fun isThereInternetConnection(context: Context?): Boolean {
        var isConnected = false

        if (context != null) {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting)
        }

        return isConnected
    }

    fun isWifiConnected(context: Context): Boolean {
        return isConnected(context, ConnectivityManager.TYPE_WIFI)
    }

    fun isMobileConnected(context: Context): Boolean {
        return isConnected(context, ConnectivityManager.TYPE_MOBILE)
    }

    private fun isConnected(context: Context, type: Int): Boolean {
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            val networkInfo = connMgr.getNetworkInfo(type)
            return networkInfo != null && networkInfo.isConnected
        } else {
            return isConnected(connMgr, type)
        }
    }

    fun isConnectedToInternet(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return Objects.requireNonNull(cm).activeNetworkInfo != null && cm.activeNetworkInfo!!
            .isConnectedOrConnecting
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun isConnected(connMgr: ConnectivityManager, type: Int): Boolean {
        val networks = connMgr.allNetworks
        var networkInfo: NetworkInfo?
        for (mNetwork in networks) {
            networkInfo = connMgr.getNetworkInfo(mNetwork)
            if (networkInfo != null && networkInfo.type == type && networkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}
