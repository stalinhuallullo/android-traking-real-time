package gob.pe.msi.trakingrealtime

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager

class MsiApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(
            "navigation_channel",
            "Navigation Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(serviceChannel)
    }
}