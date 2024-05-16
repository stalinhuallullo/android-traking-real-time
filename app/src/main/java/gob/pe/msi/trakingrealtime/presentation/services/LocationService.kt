package gob.pe.msi.trakingrealtime.presentation.services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import gob.pe.msi.trakingrealtime.presentation.MainActivity
import gob.pe.msi.trakingrealtime.R
import gob.pe.msi.trakingrealtime.presentation.adapter.LocationRealTimeAdapter
import gob.pe.msi.trakingrealtime.presentation.model.LocationModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LocationService: Service() {
    private val TAG: String = "LOCATION_SERV"

    private val NOTIFICATION_CHANNEL_ID: String = "location_service_channel"
    private val NOTIFICATION_CHANNEL_NAME: String = "location_service_channel"
    private val NOTIFICATION_ID: Int = 1
    private val NOTIFICATION_CONTENT_TITLE: String = "Sharing Location"
    private val NOTIFICATION_CONTENT_TEXT: String = "Your location is currently being shared"
    private val LOCATION_UPDATE_DELAY: Long = 2000//30000
    private val LOCATION_UPDATE_DELAY_FASTEST: Long = 1000

    private var locationSender: LocationSender? = null
    private var uuid: String? = null
    private var iteraciones: Int = 1


    private lateinit var locationRequest: LocationRequest
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback


    companion object {
        var IS_SERVICE_RUNNING = false

        fun startService(context: Context, message: String) {
            val startIntent = Intent(context, LocationService::class.java)
            ContextCompat.startForegroundService(context, startIntent)
        }
        fun stopService(context: Context) {
            val stopIntent = Intent(context, LocationService::class.java)
            context.stopService(stopIntent)
        }



    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        IS_SERVICE_RUNNING = true

        locationRequest = LocationRequest.create();
        locationRequest.priority = Priority.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = LOCATION_UPDATE_DELAY
        locationRequest.fastestInterval = LOCATION_UPDATE_DELAY_FASTEST

        fusedLocationProviderClient = LocationServices
            .getFusedLocationProviderClient(this@LocationService)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                println("===== onCreate 3333 ===== ")
                if (locationResult.locations.size > 0) {
                    val index = locationResult.locations.size - 1
                    val latitude = locationResult.locations[index].latitude
                    val longitude = locationResult.locations[index].longitude


                    val hours = getHours()
                    val infoLog = "Location : Latitude: $latitude | Longitude: $longitude | Hora: $hours"

                    Toast.makeText(this@LocationService, infoLog, Toast.LENGTH_SHORT).show()

                    //val newLocation = LocationModel((20..50).random(), latitude.toString(), longitude.toString(), hours)

                    val intent = Intent("gob.pe.msi.trakingrealtime.ADD_ITEM")
                    intent.putExtra("count", iteraciones)
                    intent.putExtra("latitude", latitude.toString())
                    intent.putExtra("longitude", longitude.toString())
                    intent.putExtra("hours", hours)
                    LocalBroadcastManager.getInstance(this@LocationService).sendBroadcast(intent)

                    iteraciones++
                    // Envía la información a la actividad mediante un intent broadcast

                    uuid = "01"
                    GlobalScope.launch{
                        locationSender?.sendLocation(uuid!!, latitude.toString(), longitude.toString(), hours)
                    }
                }
            }
        }
    }


    fun getHours(): String{
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale("es", "ES"))
        val currentdate = sdf.format(Date())
        val fechaactual = sdf.parse(currentdate)
        // Log.d("fechaactual", fechaactual.toString())
        return fechaactual.toString()

    }

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        /**
         * Sample app
         *
         * @link https://github.com/android/location-samples/tree/432d3b72b8c058f220416958b444274ddd186abd/LocationUpdatesForegroundService
         *
         * */
        println("=======  onStartCommand ======")




        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
        )

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(notificationManager)

        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false)
            .setSmallIcon(R.drawable.ic_location)
            .setContentTitle(NOTIFICATION_CONTENT_TITLE)
            .setContentText(NOTIFICATION_CONTENT_TEXT)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(NOTIFICATION_ID, notification)

        locationSender = LocationSender.getInstance()
        //uuid = PreferenceManager.getDefaultSharedPreferences(this).getString("uuid", "").toString()

        //val sharedPreferences = this.getSharedPreferences("LocationService", Context.MODE_PRIVATE)
        //uuid = sharedPreferences.getString("uuid", "") ?: ""

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())


        Log.e(TAG, "NOTIFICATION POSTED")
        return START_NOT_STICKY
    }


    private fun createNotificationChannel(notificationManager: NotificationManager) {
        println("=======  createNotificationChannel ======")
        val serviceChannel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(serviceChannel)
    }

    override fun onDestroy() {
        println("=======  onDestroy ======")
        super.onDestroy()
        IS_SERVICE_RUNNING = false

        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

}