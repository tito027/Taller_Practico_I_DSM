package com.example.tallerpracticoi_dsm

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentContainerView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var averageScoreView: FragmentContainerView
    private lateinit var calculatorView: FragmentContainerView
    private lateinit var salaryView: FragmentContainerView
    private lateinit var studentScoreView: FragmentContainerView


    override fun onCreate(savedInstanceState: Bundle?) {
        //Thread.sleep(20000)
        //Cargar el tema por defecto
        setTheme(R.style.Theme_TallerPracticoIDSM)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setContentView(R.layout.activity_register)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)

        setSupportActionBar(toolbar)

        val transaction = supportFragmentManager.beginTransaction()
        drawer = findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        transaction.replace(R.id.frameLayout, StudentsScore())
        transaction.commit()
        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val transaction = supportFragmentManager.beginTransaction()
        when(item.itemId) {
            R.id.nav_exercise_one -> {
                transaction.replace(R.id.frameLayout, StudentsScore())
            }
            R.id.nav_exercise_two -> {
                transaction.replace(R.id.frameLayout, WorkerList())
            }
            R.id.nav_exercise_three -> {
                transaction.replace(R.id.frameLayout, CalculatorFragment())
            }
        }
        transaction.commit()

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