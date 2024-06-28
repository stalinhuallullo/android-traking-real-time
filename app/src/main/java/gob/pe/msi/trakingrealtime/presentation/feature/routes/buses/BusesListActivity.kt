package gob.pe.msi.trakingrealtime.presentation.feature.routes.buses

import android.app.Activity
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gob.pe.msi.trakingrealtime.R
import gob.pe.msi.trakingrealtime.data.model.HttpResponseBus
import gob.pe.msi.trakingrealtime.presentation.common.utils.OnSingleClickListener
import gob.pe.msi.trakingrealtime.presentation.feature.routes.buses.adapter.BusesListAdapter
import gob.pe.msi.trakingrealtime.presentation.feature.routes.buses.model.Bus
import gob.pe.msi.trakingrealtime.utils.Constants
import gob.pe.msi.trakingrealtime.utils.Tools

class BusesListActivity : AppCompatActivity(), BusesListView, BusesListAdapter.BusesListener {

    private lateinit var recyclerView: RecyclerView
    private var selected: Bus? = null
    private var busHttp: HttpResponseBus? = null
    private lateinit var  adapter: BusesListAdapter
    private lateinit var toolbar: Toolbar
    private lateinit var btnSave: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buses_list)

        initToolbar()
        initComponents()
        initRVPresentations()
        initClickListener()
    }

    private fun initToolbar() {
        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.navigationIcon?.colorFilter = PorterDuffColorFilter(resources.getColor(R.color.grey_60, theme), PorterDuff.Mode.SRC_ATOP)
        toolbar.setBackgroundColor(resources.getColor(R.color.white, theme))
        toolbar.setTitleTextColor(resources.getColor(R.color.black, theme))
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Buses disponibles"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        Tools.setSystemBarColor(this, R.color.white)
        Tools.setSystemBarLight(this)

        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

    }

    fun initComponents() {
        btnSave = findViewById(R.id.btnSave)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            selected = intent.getParcelableExtra(Constants.BUS_RESPONSE_KEY, Bus::class.java)
            busHttp = intent.getParcelableExtra(Constants.EXTRA_ROUTE_METHOD, HttpResponseBus::class.java)
            //routesHttp = intent.getParcelableExtra(Constants.EXTRA_ROUTE_METHOD, HttpResponse::class.java)
        } else {
            selected = intent.getParcelableExtra(Constants.BUS_RESPONSE_KEY)
            busHttp = intent.getParcelableExtra(Constants.EXTRA_ROUTE_METHOD)
        }
    }

    fun initClickListener(){
        btnSave.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                selected?.let {
                    val resultIntent = Intent()
                    resultIntent.putExtra(Constants.BUS_RESPONSE_KEY, it)
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                } ?: Toast.makeText(this@BusesListActivity, "No route selected", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun validateButtonSaved() {
        if (selected != null) {
            btnSave.isEnabled = true
        } else {
            btnSave.isEnabled = false
        }
    }
    fun initRVPresentations() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.itemAnimator = null
        var routes: List<Bus> =  busHttp!!.Datos?.map { Bus(it.CODBUS, it.TXTBUS, it.TXTPLACA) }!!

        adapter = BusesListAdapter(routes, selected,this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun updateProduct(bus: Bus, position: Int) {
        selected = Bus(bus.id, bus.plate, bus.brand)
        validateButtonSaved()

    }

    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }

}