package gob.pe.msi.trakingrealtime.presentation.feature.routes.register

import android.app.Activity
import android.content.Context
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
import gob.pe.msi.trakingrealtime.presentation.common.utils.OnSingleClickListener
import gob.pe.msi.trakingrealtime.presentation.common.widget.CustomItemBig
import gob.pe.msi.trakingrealtime.presentation.feature.routes.buses.BusesListActivity
import gob.pe.msi.trakingrealtime.presentation.feature.routes.buses.model.Bus
import gob.pe.msi.trakingrealtime.presentation.feature.routes.routerList.RoutesListActivity
import gob.pe.msi.trakingrealtime.presentation.feature.routes.routerList.model.Route
import gob.pe.msi.trakingrealtime.presentation.feature.routes.utils.UtilsRoutes
import gob.pe.msi.trakingrealtime.presentation.feature.traking.TrackingActivity
import gob.pe.msi.trakingrealtime.utils.Constants
import gob.pe.msi.trakingrealtime.utils.Tools


class RoutesActivity : AppCompatActivity(), View.OnClickListener, CustomItemBig.ItemListener {

    private lateinit var toolbar: Toolbar
    lateinit var ciRoutes: CustomItemBig
    lateinit var ciBuses: CustomItemBig
    lateinit var listener: RoutesListener
    private lateinit var btnSave: TextView
    var selectedRoute: Route? = null
    var selectedBus: Bus? = null
    private lateinit var startForResultRoutes : ActivityResultLauncher<Intent>
    private lateinit var startForResultBuses : ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_routes)
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


    override fun attachBaseContext(context: Context?) {
        super.attachBaseContext(context)
        if (context is RoutesListener) {
            this.listener = context
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_dashboard, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                //Toast.makeText(applicationContext, "MENU", Toast.LENGTH_SHORT).show()
                //finish()
                toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
            }
            else -> {
                Toast.makeText(applicationContext, item.title, Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        when(v?.id){
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
                ContextCompat.getDrawable(this, UtilsRoutes.changeImageByText(selectedRoute!!.route))
            )
            ciRoutes.setTitle(selectedRoute!!.routeName)
            ciRoutes.setSubTitle(selectedRoute!!.direction)
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
        /*val paymentsAvailable: MutableList<PaymentMethod> = ArrayList()
        paymentsRecurrency.forEach { t ->
            paymentsCurrent.forEach { c ->
                if (t.id == c.id) {
                    paymentsAvailable.add(c)
                }
            }
        }*/

        val intent = Intent(applicationContext, RoutesListActivity::class.java)
        selectedRoute?.let {
            intent.putExtra(Constants.ROUTE_RESPONSE_KEY, it)
        }
        startForResultRoutes.launch(intent)
        //listener.goToRoutes()
    }

    fun validateBusesAvailable() {
        val buses = listOf(
            Bus(1, "XYZ-123", "TOYOTA"),
            Bus(2, "AAA-123", "TOYOTA"),
            Bus(3, "BBB-123", "TOYOTA"),
            Bus(4, "CCC-123", "TOYOTA"),
            Bus(5, "DDD-123", "TOYOTA"),
            Bus(6, "EEE-123", "TOYOTA"),
        )
        val intent = Intent(applicationContext, BusesListActivity::class.java)
        selectedBus?.let {
            intent.putExtra(Constants.BUS_RESPONSE_KEY, it)
        }
        //intent.putParcelableArrayListExtra(Constants.BUSES_DATA, buses)
        intent.putParcelableArrayListExtra(Constants.BUSES_DATA, ArrayList(buses))
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
                //currentRoutes()
            }
            ciBuses.tag -> {
                validateBusesAvailable()
                //currentBuses()
            }
        }
    }
    interface RoutesListener {
        //fun goToRoutes(payments: List<PaymentMethod>)
        fun goToRoutes()
    }

}