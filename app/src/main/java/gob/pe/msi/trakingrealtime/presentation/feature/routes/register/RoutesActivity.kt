package gob.pe.msi.trakingrealtime.presentation.feature.routes.register

import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import gob.pe.msi.trakingrealtime.R
import gob.pe.msi.trakingrealtime.presentation.common.widget.CustomItemBig
import gob.pe.msi.trakingrealtime.presentation.feature.routes.utils.UtilsRoutes
import gob.pe.msi.trakingrealtime.utils.Tools

class RoutesActivity : AppCompatActivity(), View.OnClickListener, CustomItemBig.ItemListener {

    lateinit var ciRoutes: CustomItemBig
    lateinit var ciBuses: CustomItemBig
    lateinit var listener: RoutesListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_routes)
        initToolbar()
        initComponent()
        initItems()

    }

    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_notes)
        toolbar.navigationIcon!!
            .setColorFilter(resources.getColor(R.color.grey_80), PorterDuff.Mode.SRC_ATOP)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = ""
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        Tools.setSystemBarColor(this, android.R.color.white)
        Tools.setSystemBarLight(this)

    }

    private fun initComponent() {
        ciRoutes = findViewById(R.id.ciRoutes)
        ciBuses = findViewById(R.id.ciBuses)

        ciRoutes.listener = this
        ciBuses.listener = this
    }
    fun initItems() {
        ciRoutes.setOnclickItem(this)
        ciBuses.setOnclickItem(this)
    }

    override fun attachBaseContext(context: Context?) {
        super.attachBaseContext(context)
        println("====================== attachBaseContext ====================")
        if (context is RoutesListener) {
            this.listener = context
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_dashboard, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        /*if (item.itemId == android.R.id.home) {
            //onBackChecker()
        } else if (item.itemId == R.id.action_refresh) {
            //loadWebFromUrl()
        } else if (item.itemId == R.id.action_browser) {
            Tools.directLinkToBrowser(this, url)
        }
        return super.onOptionsItemSelected(item)*/


        if (item.itemId == android.R.id.home) {
            Toast.makeText(applicationContext, "MENU", Toast.LENGTH_SHORT).show()
            //finish()
        } else {
            Toast.makeText(applicationContext, item.title, Toast.LENGTH_SHORT).show()
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

    fun currentRoutes() {
        ciRoutes.setVisibleDetail(true)
        ciRoutes.setDrawableResource(ContextCompat.getDrawable(this, UtilsRoutes.changeImageByText("1")))
        ciRoutes.setTitle("NORTE CENTRO FINANCIERO")
        ciRoutes.setSubTitle("Guardia Civil -> Av. Parque Norte")
    }

    fun currentBuses() {
        ciBuses.setVisibleDetail(true)
        ciBuses.setDrawableResource(ContextCompat.getDrawable(this, R.drawable.expresov2))
        ciBuses.setTitle("XYZ-224")
        ciBuses.setSubTitle("Toyota -> 2024")
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
        //listener.goToRoutes(paymentsAvailable)
        listener.goToRoutes()
    }

    override fun testClick(tag: String?) {
        when (tag) {
            ciRoutes.tag -> {
                validateRoutesAvailable()
                currentRoutes()
            }
            ciBuses.tag -> {
                currentBuses()
            }
        }
    }
    interface RoutesListener {
        //fun goToRoutes(payments: List<PaymentMethod>)
        fun goToRoutes()
    }

}