package gob.pe.msi.trakingrealtime.presentation.feature.traking

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.PorterDuff
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import gob.pe.msi.trakingrealtime.R
import gob.pe.msi.trakingrealtime.presentation.feature.traking.adapter.LocationRealTimeAdapter
import gob.pe.msi.trakingrealtime.presentation.feature.traking.model.LocationModel
import gob.pe.msi.trakingrealtime.presentation.feature.traking.services.LocationService
import gob.pe.msi.trakingrealtime.utils.Tools


class TrackingActivity : AppCompatActivity(), View.OnClickListener, EasyPermissions.PermissionCallbacks {
    private val TAG: String = "MAIN ACTIVITY"

    private lateinit var toolbar: Toolbar

    //private lateinit var trackFriend: Button
    private lateinit var trackerBtn: Button

    private val PERMISSION_LOCATION_REQUEST_CODE: Int = 1

    private var lm: LocationManager? = null;


    private var recyclerView: RecyclerView? = null;
    val listLocationRealTime: MutableList<LocationModel> = mutableListOf()
    private val  adapter: LocationRealTimeAdapter = LocationRealTimeAdapter(ArrayList())
    private lateinit var receiver: BroadcastReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_traking)
        initToolbar()
        if(!hasLocationPermissions()) {
            requestLocationPermission()
        }

        //toolbar = findViewById(R.id.topAppBar)
        //setSupportActionBar(toolbar)
        //supportActionBar?.title = "MSI TRAKING BUS"

        //trackFriend = findViewById<Button>(R.id.trackAFriendBtn)
        trackerBtn = findViewById(R.id.trackerBtn)



        //trackFriend.setOnClickListener(this)
        trackerBtn.setOnClickListener(this)

        if(!LocationService.IS_SERVICE_RUNNING) trackerBtn.text="Start Tracker"
        else trackerBtn.text="Stop Tracker"

        lm = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        setupRecyclerView()
        setupBroadcastReceiver()

    }

    private fun initToolbar() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.setNavigationIcon(R.drawable.ic_menu)
        toolbar.navigationIcon!!
            .setColorFilter(resources.getColor(R.color.grey_60), PorterDuff.Mode.SRC_ATOP)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = ""
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        Tools.setSystemBarColor(this, R.color.grey_5)
        Tools.setSystemBarLight(this)
    }

    private fun setupBroadcastReceiver() {
        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                val count = intent.getIntExtra("count", 0)
                val latitude = intent.getStringExtra("latitude")
                val longitude = intent.getStringExtra("longitude")
                val hours = intent.getStringExtra("hours")

                if(latitude != null && longitude != null && hours != null){
                    //recyclerView?.scrollToPosition(adapter.itemCount -1)
                    adapter.addLocation(LocationModel(count, latitude, longitude, hours))
                }
            }
        }
    }

    private fun hasLocationPermissions() =
        EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)

    private fun requestLocationPermission() {
        EasyPermissions.requestPermissions(
            this,
            "This application cannot work without location permission.",
            PERMISSION_LOCATION_REQUEST_CODE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    override fun onClick(v: View?) {
        println("LocationService.IS_SERVICE_RUNNING =======> " + LocationService.IS_SERVICE_RUNNING)
        if(trackerBtn.id == v?.id){
            if(!LocationService.IS_SERVICE_RUNNING) {
                Toast.makeText(this, "== Prendiendo ==", Toast.LENGTH_SHORT).show()
                if(EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    if (lm?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true) {
                        LocationService.startService(this, "Location Service is running...")

                        (v as Button).text="Stop Tracker"
                    }
                    else {
                        Toast.makeText(this, "Turn on GPS.", Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    Toast.makeText(this, "Location permission not granted", Toast.LENGTH_SHORT).show()
                    EasyPermissions.requestPermissions(
                        this,
                        "This application cannot work without location permission.",
                        PERMISSION_LOCATION_REQUEST_CODE,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                }
            }
            else {
                Toast.makeText(this, "== Apagando por boton ==", Toast.LENGTH_SHORT).show()
                LocationService.stopService(this)

                (v as Button).text="Start Tracker"
            }
        }

    }


    private fun setupRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        //val people = mutableListOf<LocationModel>()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    /*fun addLocationAdapter (lng: Double, lat: Double, hours: String) {
        println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa   " + lng.toString())
        adapter.addLocation(LocationModel(1, lng.toString(), lat.toString(), hours))
    }*/

    fun addItemListLocationMock() {
        val locations = listOf(
            LocationModel(1,"132", "30", "Doe"),
            LocationModel(100,"Jane", "25", "Smith"),
            LocationModel(200,"Michael", "35", "Johnson"),
            LocationModel(1000,"132", "30", "Doe"),
            LocationModel(300,"Jane", "25", "Smith"),
            LocationModel(400,"Michael", "35", "Johnson")
        ).toMutableList()


        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = LocationRealTimeAdapter(locations)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if(EasyPermissions.somePermissionDenied(this, perms.first())) {
            SettingsDialog.Builder(this@TrackingActivity).build().show()
        } else {
            requestLocationPermission()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Toast.makeText(this, "Location Share is ready", Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this@TrackingActivity).registerReceiver(receiver, IntentFilter("gob.pe.msi.trakingrealtime.ADD_ITEM"))
    }


    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this@TrackingActivity).unregisterReceiver(receiver)

        Toast.makeText(this, "== Apagando por cerrar app ==", Toast.LENGTH_SHORT).show()
        LocationService.stopService(this)
    }
    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(this@TrackingActivity).unregisterReceiver(receiver)

    }
}
