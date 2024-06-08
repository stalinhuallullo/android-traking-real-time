package gob.pe.msi.trakingrealtime.presentation.feature.routes.routerList

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gob.pe.msi.trakingrealtime.R
import gob.pe.msi.trakingrealtime.domain.RoutePresentation
import gob.pe.msi.trakingrealtime.presentation.common.utils.OnSingleClickListener
import gob.pe.msi.trakingrealtime.presentation.feature.routes.routerList.adapter.RouteAdapter
import gob.pe.msi.trakingrealtime.presentation.feature.routes.routerList.model.RouteViewModel
import gob.pe.msi.trakingrealtime.presentation.feature.routes.routerList.model.dto.RouteDto
import gob.pe.msi.trakingrealtime.utils.Tools

class RoutesListActivity : AppCompatActivity(), RouteAdapter.RouteListener {


    private lateinit var recyclerView: RecyclerView
    private var selectedRoute: RouteDto? = null
    private lateinit var  adapter: RouteAdapter
    private lateinit var toolbar: Toolbar
    private lateinit var btnSave: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routes_list)

        initToolbar()
        initComponents()
        initRVPresentations()
        initClickListener()
    }

    private fun initToolbar() {
        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.navigationIcon!!
            .setColorFilter(resources.getColor(R.color.grey_60), PorterDuff.Mode.SRC_ATOP)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Rutas disponibles"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        Tools.setSystemBarColor(this, R.color.white)
        toolbar.setBackgroundColor(resources.getColor(R.color.white, theme))
        toolbar.setTitleTextColor(resources.getColor(R.color.black, theme))
        Tools.setSystemBarLight(this)

    }

    fun initComponents() {
        btnSave = findViewById(R.id.btnSave)
    }

    fun initClickListener(){
        btnSave.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                Toast.makeText(baseContext, "Selected: ", Toast.LENGTH_SHORT).show()
                /*if (checkTerms == 0) {
                    return
                }
                if (KEY_ACTIVE_PACK.equals(statusPack)) {
                    showAlertUpdatePackRec()
                } else {
                    confirmCartValue = true
                    presenter.cart()
                    AnalyticsTrackerATM.sendEvent("form_checkout_detalle", "Form :: Checkout", "Click", "Activar envíos")
                }*/
            }
        })
    }

    fun initRVPresentations() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.itemAnimator = null // Deshabilitar animaciones
        val presentation = listOf(
            RoutePresentation(RouteDto("PERIFERICA", "RUTA 1", "Av. La Fontana Nro. 466 Int. 1032 Urb. San Cesar Ii Etapa")),
            RoutePresentation(RouteDto("NORTE CENTRO FINANCIERO", "RUTA 2", "Av. La Molina Nro. 820 Int. 1 Urb. Ampliacion Residencial Monterrico")),
            RoutePresentation(RouteDto("SUR CENTRO FINANCIERO", "RUTA 3", "Av. Flora Tristan 691 Mz. O Lt. 3 Esq. Calle Piura N° 106-108 Tda. 1,2,3 Urb. Santa Patricia")),

            RoutePresentation(RouteDto("PERIFERICA", "RUTA 4", "Av. La Fontana Nro. 466 Int. 1032 Urb. San Cesar Ii Etapa")),
            RoutePresentation(RouteDto("NORTE CENTRO FINANCIERO", "RUTA 5", "Av. La Molina Nro. 820 Int. 1 Urb. Ampliacion Residencial Monterrico")),
            RoutePresentation(RouteDto("SUR CENTRO FINANCIERO", "RUTA 6", "Av. Flora Tristan 691 Mz. O Lt. 3 Esq. Calle Piura N° 106-108 Tda. 1,2,3 Urb. Santa Patricia")),

            RoutePresentation(RouteDto("PERIFERICA", "RUTA 7", "Av. La Fontana Nro. 466 Int. 1032 Urb. San Cesar Ii Etapa")),
            RoutePresentation(RouteDto("NORTE CENTRO FINANCIERO", "RUTA 8", "Av. La Molina Nro. 820 Int. 1 Urb. Ampliacion Residencial Monterrico")),
            RoutePresentation(RouteDto("SUR CENTRO FINANCIERO", "RUTA 9", "Av. Flora Tristan 691 Mz. O Lt. 3 Esq. Calle Piura N° 106-108 Tda. 1,2,3 Urb. Santa Patricia")),
        )

        adapter = RouteAdapter(presentation, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }

    fun onPharmacyClick(pharmacy: RouteDto) {
        //selectedPharmacy = pharmacy
        // Aquí puedes guardar el objeto seleccionado o mostrarlo como desees
        Toast.makeText(this, "Selected: ${pharmacy.routeName}", Toast.LENGTH_SHORT).show()

        // Si necesitas pasar la información a otra actividad o fragmento, puedes hacerlo aquí
        /*val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("PHARMACY_NAME", pharmacy.name)
            putExtra("PHARMACY_ADDRESS", pharmacy.address)
            putExtra("PHARMACY_DISTANCE", pharmacy.distance)
            putExtra("PHARMACY_HOURS", pharmacy.hours)
        }
        startActivity(intent)*/
    }

    override fun updateProduct(presentation: RoutePresentation, position: Int) {
        /*if (presentation) {
            btnSave.isEnabled = true
        } else {
            binding.btnSave.isEnabled = false
        }*/

        selectedRoute = RouteDto(presentation.routeName!!, presentation.route!!,presentation.direction!!)
        btnSave.isEnabled = true
        //selectedRoute = presentation.routeDto
        // Aquí puedes guardar el objeto seleccionado o mostrarlo como desees
        //Toast.makeText(this, "Selected: ${selectedRoute!!.routeName}", Toast.LENGTH_SHORT).show()
    }
}