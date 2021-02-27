package com.manohar.ecommerceshoppingportal.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.google.firebase.FirebaseApp
import com.manohar.ecommerceshoppingportal.R
import com.manohar.ecommerceshoppingportal.model.TabAdapter
import com.manohar.ecommerceshoppingportal.view.home.HomeFragment
import com.manohar.ecommerceshoppingportal.view.ui.main.MyCartFragment2
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.ibrahimsn.lib.NiceBottomBar


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val adapter = TabAdapter(
        supportFragmentManager
    )
    var viewpager:NonSwipeableViewPager?=null
    //var bottomNavigationBar:BottomNavigationBar?=null
    var bottomBar:NiceBottomBar?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        FirebaseApp.initializeApp(this)

        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        nav_view.bringToFront()

        bottomBar =
            findViewById<View>(R.id.bottom_navigation_bar) as NiceBottomBar

         viewpager = findViewById<NonSwipeableViewPager>(R.id.pager)


        CoroutineScope(IO).launch {
            adapter.addFragment(HomeFragment(), "1")
            adapter.addFragment(blogFragment(), "2")
            adapter.addFragment(PlacedOrdersFragment(), "3")
            adapter.addFragment(MyCartFragment2(), "4")
            adapter.addFragment(ProfileFragment(), "5")
            withContext(Main)
            {
                viewpager!!.setAdapter(adapter)
                viewpager!!.adapter = adapter
                viewpager!!.offscreenPageLimit = 5
            }
        }




    }
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.cart -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_gallery -> {
                // Handle the camera action

            }
            R.id.nav_home -> {

            }
            R.id.nav_slideshow -> {

            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onStart() {
        super.onStart()

       bottomBar!!.onItemSelected = {
           when (it) {
               0 -> viewpager!!.currentItem=0
               1 -> viewpager!!.currentItem=1
               2 -> viewpager!!.currentItem=2
               3 -> viewpager!!.currentItem=3
               4 -> viewpager!!.currentItem=4


           }
           bottomBar!!.bringToFront()
       }







        /*
        bottomNavigationBar!!.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener{
            override fun onTabReselected(position: Int) {

            }

            override fun onTabUnselected(position: Int) {

            }

            override fun onTabSelected(position: Int) {

                when (position) {
                    0 -> viewpager!!.currentItem=0
                    1 -> viewpager!!.currentItem=1
                    2 -> viewpager!!.currentItem=2
                    3 -> Toast.makeText(applicationContext, "3", Toast.LENGTH_SHORT).show()
                    4 -> Toast.makeText(applicationContext, "4", Toast.LENGTH_SHORT).show()


                }
                bottomNavigationBar!!.bringToFront()
            }
        })

         */

    }



}