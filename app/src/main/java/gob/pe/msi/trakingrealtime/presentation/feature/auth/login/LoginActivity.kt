package gob.pe.msi.trakingrealtime.presentation.feature.auth.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.snackbar.Snackbar
import gob.pe.msi.trakingrealtime.R
import gob.pe.msi.trakingrealtime.presentation.feature.dashboard.DashboardActivity
import gob.pe.msi.trakingrealtime.utils.Tools

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var btnLogin: AppCompatButton
    lateinit var progressBar: ProgressBar
    lateinit var parentView: View
    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }*/



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        parentView = findViewById(android.R.id.content)
        btnLogin = findViewById(R.id.btnLogin)
        progressBar = findViewById(R.id.progressBar)

        // event clicks
        btnLogin.setOnClickListener(this)


        // tool bar
        Tools.setSystemBarColor(this, R.color.white)
        Tools.setSystemBarLight(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnLogin->{
                progressBar.visibility = View.VISIBLE
                btnLogin.visibility = View.GONE
                Handler(Looper.getMainLooper()).postDelayed({
                    progressBar.setVisibility(View.GONE)
                    btnLogin.setVisibility(View.VISIBLE)
                    Snackbar.make(parentView, "Login data submitted", Snackbar.LENGTH_SHORT).show()

                    val intent = Intent(this, DashboardActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish()
                }, 2000)
            }
        }
    }
}