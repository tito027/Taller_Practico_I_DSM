package com.example.tallerpracticoi_dsm

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentContainerView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var averageScoreView: FragmentContainerView
    private lateinit var calculatorView: FragmentContainerView
    private lateinit var salaryView: FragmentContainerView


    override fun onCreate(savedInstanceState: Bundle?) {
        //Thread.sleep(20000)
        //Cargar el tema por defecto
        setTheme(R.style.Theme_TallerPracticoIDSM)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        averageScoreView = findViewById(R.id.averageScoreView)
        calculatorView = findViewById(R.id.calculatorView)
        salaryView = findViewById(R.id.salaryView)
        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_exercise_one -> {
                averageScoreView.isVisible = true
                calculatorView.isVisible = false
                salaryView.isVisible = false
            }
            R.id.nav_exercise_two -> {
                averageScoreView.isVisible = false
                calculatorView.isVisible = false
                salaryView.isVisible = true

            }
            R.id.nav_exercise_three -> {
                averageScoreView.isVisible = false
                calculatorView.isVisible = true
                salaryView.isVisible = false
            }
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}