package gob.pe.msi.trakingrealtime.presentation.feature.splash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import gob.pe.msi.trakingrealtime.R
import gob.pe.msi.trakingrealtime.presentation.feature.traking.TrakingActivity
import java.util.UUID


class SplashScreen : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initBuild()
    }


    fun initBuild() {
        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        sharedPreferences = getSharedPreferences("your_preference_name", Context.MODE_PRIVATE)

        if (sharedPreferences.getString("uuid", "") == "") {
            sharedPreferences.edit().putString("uuid", UUID.randomUUID().toString()).apply()
        }

        val intent = Intent(this, TrakingActivity::class.java)
        startActivity(intent)
        finish()
    }

}