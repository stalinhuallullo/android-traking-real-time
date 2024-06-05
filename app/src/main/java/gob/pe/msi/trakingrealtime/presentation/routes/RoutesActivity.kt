package gob.pe.msi.trakingrealtime.presentation.routes

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import gob.pe.msi.trakingrealtime.R
import gob.pe.msi.trakingrealtime.utils.Tools

class RoutesActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_routes)
        initToolbar()
        initComponent()
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

}