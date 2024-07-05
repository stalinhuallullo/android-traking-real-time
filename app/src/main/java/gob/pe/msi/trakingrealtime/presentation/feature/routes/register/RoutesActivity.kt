package gob.pe.msi.trakingrealtime.presentation.feature.routes.register

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import gob.pe.msi.trakingrealtime.R
import gob.pe.msi.trakingrealtime.data.model.HttpResponseBus
import gob.pe.msi.trakingrealtime.data.model.HttpResponseRoutes
import gob.pe.msi.trakingrealtime.data.net.service.impl.BusesService
import gob.pe.msi.trakingrealtime.data.net.service.impl.RoutesService
import gob.pe.msi.trakingrealtime.data.repository.BusRepository
import gob.pe.msi.trakingrealtime.data.repository.RouteRepository
import gob.pe.msi.trakingrealtime.databinding.ActivityRoutesBinding
import gob.pe.msi.trakingrealtime.domain.entity.ResponseRoute
import gob.pe.msi.trakingrealtime.domain.executor.JobExecutor
import gob.pe.msi.trakingrealtime.domain.executor.UIThread
import gob.pe.msi.trakingrealtime.domain.interactor.RouteUseCase
import gob.pe.msi.trakingrealtime.presentation.common.utils.OnSingleClickListener
import gob.pe.msi.trakingrealtime.presentation.common.widget.CustomItemBig
import gob.pe.msi.trakingrealtime.presentation.feature.navegation.NavegationActivity
import gob.pe.msi.trakingrealtime.presentation.feature.routes.buses.BusesListActivity
import gob.pe.msi.trakingrealtime.presentation.feature.routes.buses.model.Bus
import gob.pe.msi.trakingrealtime.presentation.feature.routes.routerList.RoutesListActivity
import gob.pe.msi.trakingrealtime.presentation.feature.routes.routerList.model.Route
import gob.pe.msi.trakingrealtime.presentation.feature.traking.TrackingActivity
import gob.pe.msi.trakingrealtime.utils.Constants
import gob.pe.msi.trakingrealtime.utils.Tools

class RoutesActivity :
    AppCompatActivity(),
    RouteContract.View,
    View.OnClickListener,
    CustomItemBig.ItemListener {


    private lateinit var binding: ActivityRoutesBinding
    private lateinit var routePresenter: RoutePresenter
    private lateinit var startForResultRoutes : ActivityResultLauncher<Intent>
    private lateinit var startForResultBuses : ActivityResultLauncher<Intent>


    private lateinit var toolbar: Toolbar
    private lateinit var ciRoutes: CustomItemBig
    private lateinit var ciBuses: CustomItemBig
    private lateinit var btnSave: TextView

    private var selectedRoute: Route? = null
    private var selectedBus: Bus? = null

    private var routesHttp: HttpResponseRoutes? = null
    private var busHttp: HttpResponseBus? = null
    private var busHttpsss: ResponseRoute? = null

    private val REQUEST_CODE_LOCATION_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoutesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (isTracking()) {
            navigateToNavigationActivity()
        }

        initToolbar()
        initComponent()
        initClickListener()
        registerActivityResultRoutes()
        registerActivityResultBuses()

    }

    private fun initToolbar() {

        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.navigationIcon?.colorFilter = PorterDuffColorFilter(resources.getColor(R.color.grey_60, theme), PorterDuff.Mode.SRC_ATOP)
        toolbar.setBackgroundColor(resources.getColor(R.color.white, theme))
        toolbar.setTitleTextColor(resources.getColor(R.color.black, theme))
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
        setSupportActionBar(toolbar)

        supportActionBar!!.title = "Registrar servicio"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        Tools.setSystemBarColor(this, R.color.white)
        Tools.setSystemBarLight(this)
    }

    private fun initComponent() {
        ciRoutes = findViewById(R.id.ciRoutes)
        ciBuses = findViewById(R.id.ciBuses)
        btnSave = findViewById(R.id.btnSave)


        ciRoutes.listener = this
        ciBuses.listener = this
        ciBuses.enabledComponentes(false)


        // Inicializacion de Presenter
        val routeUseCase = RouteUseCase(RouteRepository(RoutesService()), BusRepository(BusesService()), JobExecutor(), UIThread())
        routePresenter = RoutePresenter(this, routeUseCase)

        if (checkPermissions()) {
            //setupButtonClick()
        } else {
            requestPermissions()
        }
    }

    private fun checkPermissions(): Boolean {
        val fineLocation = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
        val coarseLocation = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
        val foregroundServiceLocation = ContextCompat.checkSelfPermission(this, android.Manifest.permission.FOREGROUND_SERVICE_LOCATION)
        return fineLocation == PackageManager.PERMISSION_GRANTED &&
                coarseLocation == PackageManager.PERMISSION_GRANTED &&
                foregroundServiceLocation == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this,
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.FOREGROUND_SERVICE_LOCATION
            ),
            REQUEST_CODE_LOCATION_PERMISSION
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                //setupButtonClick()
            } else {
                // Manejar el caso en que no se otorgaron los permisos
                Toast.makeText(this, "Permissions required for the app to function", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initClickListener(){
        ciRoutes.setOnclickItem(this)
        ciBuses.setOnclickItem(this)

        btnSave.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                routePresenter.onStartNavigationClicked()
                /*val intent = Intent(baseContext, TrackingMapActivity::class.java)
                intent.putExtra(Constants.REGISTER_TO_TRACKING_ROUTE, selectedRoute)
                intent.putExtra(Constants.REGISTER_TO_TRACKING_BUS, selectedBus)
                saveTrackingState(true)
                startActivity(intent)*/


                /*val intent = Intent(baseContext, TrackingActivity::class.java)
                intent.putExtra(Constants.REGISTER_TO_TRACKING_ROUTE, selectedRoute)
                intent.putExtra(Constants.REGISTER_TO_TRACKING_BUS, selectedBus)
                saveTrackingState(true)
                startActivity(intent)*/
            }
        })
    }



    override fun navigateToNavigationActivity() {
        val intent = Intent(baseContext, NavegationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra(Constants.REGISTER_TO_TRACKING_ROUTE, selectedRoute)
        intent.putExtra(Constants.REGISTER_TO_TRACKING_BUS, selectedBus)
        saveTrackingState(true)
        startActivity(intent)
        finish()
        /*val intent = Intent(this, TrackingMapActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)*/
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_dashboard, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
            }
            else -> {
                Toast.makeText(applicationContext, item.title, Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.imgProfile -> {
                Toast.makeText(applicationContext, "PROFILE", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun registerActivityResultRoutes() {
        startForResultRoutes = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // TIRAMISU onwards
                    selectedRoute = data?.getParcelableExtra(Constants.ROUTE_RESPONSE_KEY, Route::class.java)
                } else {
                    selectedRoute = data?.getParcelableExtra(Constants.ROUTE_RESPONSE_KEY)
                }
                currentRoutes()
            }
        }
    }
    private fun registerActivityResultBuses() {
        startForResultBuses = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // TIRAMISU onwards
                    selectedBus = data?.getParcelableExtra(Constants.BUS_RESPONSE_KEY, Bus::class.java)
                } else {
                    selectedBus = data?.getParcelableExtra(Constants.BUS_RESPONSE_KEY)
                }
                currentBuses()
            }
        }
    }


    private fun currentRoutes() {
        if(selectedRoute != null) {
            ciRoutes.setVisibleDetail(true)
            ciRoutes.setDrawableResource(
                //ContextCompat.getDrawable(this, UtilsRoutes.changeImageByText(selectedRoute!!.route))
                        ContextCompat.getDrawable(this, R.drawable.ic_location_blue)
            )
            ciRoutes.setTitle(selectedRoute!!.route)
            ciRoutes.setSubTitle(selectedRoute!!.direction)

            ciBuses.enabledComponentes(true)
        }
        validateButtonSaved()
    }

    private fun currentBuses() {
        if(selectedBus != null){
            ciBuses.setVisibleDetail(true)
            ciBuses.setDrawableResource(ContextCompat.getDrawable(this, R.drawable.ic_bus))
            ciBuses.setTitle(selectedBus!!.plate)
            ciBuses.setSubTitle(selectedBus!!.brand)
        }
        validateButtonSaved()
    }

    private fun validateRoutesAvailable() {
        val intent = Intent(applicationContext, RoutesListActivity::class.java)
        intent.putExtra(Constants.ROUTE_RESPONSE_KEY, selectedRoute)
        intent.putExtra(Constants.EXTRA_ROUTE_METHOD, routesHttp)
        startForResultRoutes.launch(intent)
    }

    private fun validateBusesAvailable() {
        val intent = Intent(applicationContext, BusesListActivity::class.java)
        intent.putExtra(Constants.BUS_RESPONSE_KEY, selectedBus)
        intent.putExtra(Constants.EXTRA_BUS_METHOD, busHttp)
        startForResultBuses.launch(intent)
    }

    private fun validateButtonSaved() {
        if (selectedRoute != null && selectedBus != null) {
            btnSave.isEnabled = true
        } else {
            btnSave.isEnabled = false
        }
    }

    override fun testClick(tag: String?) {
        when (tag) {
            ciRoutes.tag -> {
                routePresenter.getDataRoute()
            }
            ciBuses.tag -> {
                routePresenter.getDataBuses(selectedRoute!!.routeName)
            }
        }
    }

    override fun showLoading() {
        binding.viewLoading.root.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.viewLoading.root.visibility = View.GONE
    }

    override fun showDataRoute(response: HttpResponseRoutes) {
        routesHttp = response
        validateRoutesAvailable()
    }

    override fun showDataBus(response: HttpResponseBus) {
        busHttp = response
        validateBusesAvailable()
    }

    override fun showError(message: String) {

    }

    override fun onDestroy() {
        super.onDestroy()
        routePresenter.detachView()
    }
    private fun saveTrackingState(isTracking: Boolean) {
        val sharedPref = getSharedPreferences("tracking_prefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("isTracking", isTracking)
            apply()
        }
    }

    private fun isTracking(): Boolean {
        val sharedPref = getSharedPreferences("tracking_prefs", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("isTracking", false)
    }

}