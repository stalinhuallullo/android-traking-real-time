package gob.pe.msi.trakingrealtime.presentation.feature.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import gob.pe.msi.trakingrealtime.R
import gob.pe.msi.trakingrealtime.presentation.feature.routes.RoutesActivity
import gob.pe.msi.trakingrealtime.utils.Tools

class DashboardActivity : AppCompatActivity(), View.OnClickListener {
    //lateinit var actionBar: ActionBar
    lateinit var lytRoutesAvailable: LinearLayout
    //lateinit var intent: Intent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)
        lytRoutesAvailable = findViewById(R.id.lytRoutesAvailable)
        // EVENT CLICKS
        lytRoutesAvailable.setOnClickListener(this)
        //initToolbar()
        Tools.setSystemBarColor(this, R.color.grey_5)
        Tools.setSystemBarLight(this)

    }


    /*fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        actionBar = supportActionBar!!
        actionBar!!.setDisplayHomeAsUpEnabled(false)
        actionBar!!.setHomeButtonEnabled(false)
        actionBar.setTitle(null)
        //Tools.setSystemBarColorInt(this, Color.parseColor("#054D44"))
        Tools.setSystemBarColor(this, R.color.grey_5)
        Tools.setSystemBarLight(this)
    }*/

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_dashboard, menu)
        return true
    }*/

    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else {
            Toast.makeText(
                applicationContext,
                item.title.toString() + " clicked",
                Toast.LENGTH_SHORT
            ).show()
        }
        return super.onOptionsItemSelected(item)
    }*/

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.lytRoutesAvailable->{
                //intent = Intent(this, RoutesActivity::class.java)
                val intent = Intent(this, RoutesActivity::class.java)
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
        }
    }
}