package gob.pe.msi.trakingrealtime.presentation.services

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import gob.pe.msi.trakingrealtime.presentation.model.dto.LocationDto
import gob.pe.msi.trakingrealtime.presentation.ui.LocationViewModel

class LocationSender: LifecycleOwner  {
    private val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)
    private val locationViewModel: LocationViewModel = LocationViewModel()

    init {
        lifecycleRegistry.currentState = Lifecycle.State.INITIALIZED
    }

    fun start() {
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
    }

    fun stop() {
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
    }

    override val lifecycle: Lifecycle
        get() {
            return lifecycleRegistry
        }

    data class Location(
        val lng: Double,
        val lat: Double
    )


    fun sendLocation(uuid: String, lat: String, lng: String, hours: String) {
        println("===== ENVIANDO INFO =====")
        println("uuid ==> " + uuid)
        println("lat ==> " + lat)
        println("lng ==> " + lng)
        println("hours ==> " + hours)
        val id_user = 1

        val newLocation = LocationDto(uuid.toInt(), id_user,lat, lng, hours)
        start()
        locationViewModel.saveLocation(newLocation).observe(this, Observer { result ->
            Log.d("MainActivity", "Location : Latitude: ${result.latitude} | Longitude: ${result.longitude} | Hora: ${result.registered}")
            stop()
        })
    }

    companion object {
        private const val baseUrl:String = "tttttt"//BuildConfig.SERVER_BASE_URL
        private lateinit var INSTANCE: LocationSender

        fun getInstance(): LocationSender {
            println("getInstance =======> ")
            INSTANCE = LocationSender()
            /*if (!::INSTANCE.isInitialized) {
                INSTANCE = LocationSender(
                    Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build())
            }*/
            return INSTANCE
        }
    }



}