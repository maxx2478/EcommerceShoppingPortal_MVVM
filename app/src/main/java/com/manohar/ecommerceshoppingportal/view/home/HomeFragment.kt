package com.manohar.ecommerceshoppingportal.view.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.manohar.ecommerceshoppingportal.R
import com.manohar.ecommerceshoppingportal.model.*
import com.manohar.ecommerceshoppingportal.view.NonSwipeableViewPager
import com.manohar.ecommerceshoppingportal.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : Fragment() {



    private lateinit var homeViewModel: HomeViewModel
    private var productsAdapter: ProductsListAdapter?=null
    var root:View?=null
    private var featuredAdapter: FeaturedListAdapter?=null
    var gestureDetectorCompat:GestureDetectorCompat?=null
    private var productsService:ProductsService?=null
    var tabLayout:TabLayout?=null
    var viewpager: ViewPager?=null
    @SuppressLint("CommitPrefEdits")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_home, container, false)

         tabLayout = root!!.findViewById<TabLayout>(R.id.tabLayout)
         viewpager = root!!.findViewById(R.id.pager)
        CoroutineScope(IO).launch {

            tabLayout!!.addTab(tabLayout!!.newTab().setText("Deals"))
            tabLayout!!.addTab(tabLayout!!.newTab().setText("Top Picks"))
            tabLayout!!.addTab(tabLayout!!.newTab().setText("Recommended"))
            tabLayout!!.addTab(tabLayout!!.newTab().setText("Best Seller"))

            withContext(Main)
            {
                tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL
                val adapter = NestedTabAdapter(
                    childFragmentManager,
                    tabLayout!!.tabCount
                )
                viewpager!!.adapter = adapter
                viewpager!!.bringToFront()
                viewpager!!.offscreenPageLimit = 7
            }
        }


        return root
    }

    override fun onStart() {
        super.onStart()

        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)








        viewpager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewpager!!.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

    }


}