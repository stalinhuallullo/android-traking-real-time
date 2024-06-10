package gob.pe.msi.trakingrealtime.presentation.feature.routes.routerList

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
import gob.pe.msi.trakingrealtime.data.entity.RouteListResponseEntity
import gob.pe.msi.trakingrealtime.data.model.HttpResponse
import gob.pe.msi.trakingrealtime.data.model.HttpResponseRoutes
import gob.pe.msi.trakingrealtime.presentation.common.utils.OnSingleClickListener
import gob.pe.msi.trakingrealtime.presentation.feature.routes.routerList.adapter.RouteAdapter
import gob.pe.msi.trakingrealtime.presentation.feature.routes.routerList.model.Route
import gob.pe.msi.trakingrealtime.utils.Constants
import gob.pe.msi.trakingrealtime.utils.Tools

class RoutesListActivity : AppCompatActivity(), RouteAdapter.RouteListener {


    private lateinit var recyclerView: RecyclerView
    private var selected: Route? = null
    private var routesHttp: HttpResponseRoutes? = null
    private lateinit var  adapter: RouteAdapter
    private lateinit var toolbar: Toolbar
    private lateinit var btnSave: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routes_list)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            selected = intent.getParcelableExtra(Constants.ROUTE_RESPONSE_KEY, Route::class.java)
            //routesHttp = intent.getParcelableExtra(Constants.EXTRA_ROUTE_METHOD, HttpResponse::class.java)
        } else {
            selected = intent.getParcelableExtra(Constants.ROUTE_RESPONSE_KEY)
        }

        @Suppress("DEPRECATION")
        routesHttp = intent.getParcelableExtra(Constants.EXTRA_ROUTE_METHOD)

        initToolbar()
        initComponents()
        initRVPresentations()
        initClickListener()
    }

    private fun displayRoutes(routes: HttpResponse<List<RouteListResponseEntity>>) {
        // Tu lógica para mostrar la lista de rutas
        // Por ejemplo, puedes usar un RecyclerView para mostrar los datos

    }

    private fun initToolbar() {
        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.navigationIcon?.colorFilter = PorterDuffColorFilter(resources.getColor(R.color.grey_60, theme), PorterDuff.Mode.SRC_ATOP)
        toolbar.setBackgroundColor(resources.getColor(R.color.white, theme))
        toolbar.setTitleTextColor(resources.getColor(R.color.black, theme))
        setSupportActionBar(toolbar)

        supportActionBar!!.title = "Rutas disponibles"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        Tools.setSystemBarColor(this, R.color.white)
        Tools.setSystemBarLight(this)

        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    fun initComponents() {
        btnSave = findViewById(R.id.btnSave)


    }

    fun initClickListener(){
        btnSave.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                Toast.makeText(baseContext, "Selected: ", Toast.LENGTH_SHORT).show()
                selected?.let {
                    val resultIntent = Intent()
                    resultIntent.putExtra(Constants.ROUTE_RESPONSE_KEY, it)
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                } ?: Toast.makeText(this@RoutesListActivity, "No route selected", Toast.LENGTH_SHORT).show()
            }
        })

    }

    fun initRVPresentations() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.itemAnimator = null
        /*val routes = listOf(
            Route("PERIFERICA", "RUTA 1", "Av. La Fontana Nro. 466 Int. 1032 Urb. San Cesar Ii Etapa"),
            Route("NORTE CENTRO FINANCIERO", "RUTA 2", "Av. La Molina Nro. 820 Int. 1 Urb. Ampliacion Residencial Monterrico"),
            Route("SUR CENTRO FINANCIERO", "RUTA 3", "Av. Flora Tristan 691 Mz. O Lt. 3 Esq. Calle Piura N° 106-108 Tda. 1,2,3 Urb. Santa Patricia"),

            Route("PERIFERICA", "RUTA 4", "Av. La Fontana Nro. 466 Int. 1032 Urb. San Cesar Ii Etapa"),
            Route("NORTE CENTRO FINANCIERO", "RUTA 5", "Av. La Molina Nro. 820 Int. 1 Urb. Ampliacion Residencial Monterrico"),
            Route("SUR CENTRO FINANCIERO", "RUTA 6", "Av. Flora Tristan 691 Mz. O Lt. 3 Esq. Calle Piura N° 106-108 Tda. 1,2,3 Urb. Santa Patricia"),

            Route("PERIFERICA", "RUTA 7", "Av. La Fontana Nro. 466 Int. 1032 Urb. San Cesar Ii Etapa"),
            Route("NORTE CENTRO FINANCIERO", "RUTA 8", "Av. La Molina Nro. 820 Int. 1 Urb. Ampliacion Residencial Monterrico"),
            Route("SUR CENTRO FINANCIERO", "RUTA 9", "Av. Flora Tristan 691 Mz. O Lt. 3 Esq. Calle Piura N° 106-108 Tda. 1,2,3 Urb. Santa Patricia")
        )*/

        println("fffffffffffffffffffff ==>  $routesHttp")
        println("${routesHttp!!::class.simpleName}")


        var routes: List<Route> =  routesHttp!!.Datos?.map { Route(it.CODLINEA, it.TXTLINEA, it.TXTRGBBUS) }!!

        /*val routes: List<Route> = routesHttp!!.map { it: RouteListResponseEntity ->
            Route(it.CODLINEA, it.TXTLINEA, it.TXTRGBBUS)
        }*/
        //println(" routes fffffffffffffffffffff ==>  ${routes}")

        adapter = RouteAdapter(routes, selected,this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }

    fun validateButtonSaved() {
        if (selected != null) {
            btnSave.isEnabled = true
        } else {
            btnSave.isEnabled = false
        }
    }

    override fun updateProduct(route: Route, position: Int) {

        selected = Route(route.routeName, route.route, route.direction)
        //btnSave.isEnabled = true
        validateButtonSaved()
    }
}