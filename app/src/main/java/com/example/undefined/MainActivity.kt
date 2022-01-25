package com.example.undefined

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottomNavView)

        val controller = findNavController(R.id.nav_host_fragment)

        val appBarConfig = AppBarConfiguration(setOf(
            R.id.homeFragment,
            R.id.savedFragment,
            R.id.profileFragment
        ))

        setupActionBarWithNavController(controller, appBarConfig)
        bottomNavView.setupWithNavController(controller)

    }

}