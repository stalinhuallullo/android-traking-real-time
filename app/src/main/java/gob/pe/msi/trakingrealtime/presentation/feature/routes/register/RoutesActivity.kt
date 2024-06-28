package gob.pe.msi.trakingrealtime.presentation.feature.routes.register

import android.app.Activity
import android.content.Intent
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
import androidx.core.content.ContextCompat
import gob.pe.msi.trakingrealtime.R
import gob.pe.msi.trakingrealtime.data.model.HttpResponseBus
import gob.pe.msi.trakingrealtime.data.model.HttpResponseRoutes
import gob.pe.msi.trakingrealtime.data.net.service.impl.RoutesService
import gob.pe.msi.trakingrealtime.data.repository.RouteDataRepository
import gob.pe.msi.trakingrealtime.data.repository.RouteRepository
import gob.pe.msi.trakingrealtime.databinding.ActivityRoutesBinding
import gob.pe.msi.trakingrealtime.domain.entity.ResponseRoute
import gob.pe.msi.trakingrealtime.domain.executor.JobExecutor
import gob.pe.msi.trakingrealtime.domain.executor.UIThread
import gob.pe.msi.trakingrealtime.domain.interactor.RouteUseCase
import gob.pe.msi.trakingrealtime.presentation.common.utils.OnSingleClickListener
import gob.pe.msi.trakingrealtime.presentation.common.widget.CustomItemBig
import gob.pe.msi.trakingrealtime.presentation.feature.routes.buses.BusesListActivity
import gob.pe.msi.trakingrealtime.presentation.feature.routes.buses.model.Bus
import gob.pe.msi.trakingrealtime.presentation.feature.routes.routerList.RoutesListActivity
import gob.pe.msi.trakingrealtime.presentation.feature.routes.routerList.model.Route
import gob.pe.msi.trakingrealtime.presentation.feature.traking.TrackingActivity
import gob.pe.msi.trakingrealtime.utils.Constants
import gob.pe.msi.trakingrealtime.utils.Tools

//@AndroidEntryPoint
class RoutesActivity : AppCompatActivity(), RouteContract.View, View.OnClickListener, CustomItemBig.ItemListener {

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

    //private lateinit var viewModel: RoutesViewModel
    private var routesHttp: HttpResponseRoutes? = null
    private var busHttp: HttpResponseBus? = null
    private var busHttpsss: ResponseRoute? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoutesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initComponent()
        initClickListener()
        registerActivityResultRoutes()
        registerActivityResultBuses()

    }

    private fun initToolbar() {

        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        //toolbar.setNavigationIcon(R.drawable.ic_notes)
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

        //routePresenter = RoutePresenter(BusRepository(BusesService()), RouteRepository(RoutesService()))

        // Inicializacion de Presenter
        val routeUseCase = RouteUseCase(RouteRepository(RoutesService()), RouteDataRepository(), JobExecutor(), UIThread())
        routePresenter = RoutePresenter(this, routeUseCase)
        //routePresenter.getDataRoute()
        routePresenter.getDataBuses(1)
    }

    fun initClickListener(){
        ciRoutes.setOnclickItem(this)
        ciBuses.setOnclickItem(this)

        btnSave.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                val intent = Intent(baseContext, TrackingActivity::class.java)
                intent.putExtra(Constants.REGISTER_TO_TRACKING_ROUTE, selectedRoute)
                intent.putExtra(Constants.REGISTER_TO_TRACKING_BUS, selectedBus)
                startActivity(intent)
            }
        })

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

                if (Build.VERSION.SDK_INT >= 33) { // TIRAMISU onwards
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

                if (Build.VERSION.SDK_INT >= 33) { // TIRAMISU onwards
                    selectedBus = data?.getParcelableExtra(Constants.BUS_RESPONSE_KEY, Bus::class.java)
                } else {
                    selectedBus = data?.getParcelableExtra(Constants.BUS_RESPONSE_KEY)
                }
                currentBuses()
            }
        }
    }


    fun currentRoutes() {

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

    fun currentBuses() {
        if(selectedBus != null){
            ciBuses.setVisibleDetail(true)
            ciBuses.setDrawableResource(ContextCompat.getDrawable(this, R.drawable.ic_bus))
            ciBuses.setTitle(selectedBus!!.plate)
            ciBuses.setSubTitle(selectedBus!!.brand)
        }
        validateButtonSaved()
    }

    fun validateRoutesAvailable() {
        val intent = Intent(applicationContext, RoutesListActivity::class.java)
        selectedRoute?.let {
            intent.putExtra(Constants.ROUTE_RESPONSE_KEY, it)
        }
        routesHttp?.let {
            intent.putExtra(Constants.EXTRA_ROUTE_METHOD, it)
        }

        startForResultRoutes.launch(intent)
    }

    fun validateBusesAvailable() {
        val intent = Intent(applicationContext, BusesListActivity::class.java)
        selectedBus?.let {
            intent.putExtra(Constants.BUS_RESPONSE_KEY, it)
        }
        busHttp?.let {
            intent.putExtra(Constants.EXTRA_BUS_METHOD, it)
        }

        startForResultBuses.launch(intent)
    }

    fun validateButtonSaved() {
        if (selectedRoute != null && selectedBus != null) {
            btnSave.isEnabled = true
        } else {
            btnSave.isEnabled = false
        }
    }

    override fun testClick(tag: String?) {
        when (tag) {
            ciRoutes.tag -> {
                validateRoutesAvailable()
            }
            ciBuses.tag -> {
                validateBusesAvailable()
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
        println("response showDataRoute   ======> $response")
    }

    override fun showDataBus(response: ResponseRoute) {
        busHttpsss = response
        println("response showDataBus   ======> $response")
    }

    override fun showError(message: String) {

    }

    override fun onDestroy() {
        super.onDestroy()
        routePresenter.detachView()
    }

}