package com.manohar.ecommerceshoppingportal.model

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.manohar.ecommerceshoppingportal.view.blogFragment
import com.manohar.ecommerceshoppingportal.view.firstfragment.NestedFirstFragment
import com.manohar.ecommerceshoppingportal.view.thirdfragment.NestedThirdFragment
import com.manohar.ecommerceshoppingportal.view.home.HomeFragment
import com.manohar.ecommerceshoppingportal.view.secondfrag.NestedSecondFragment

class NestedTabAdapter(fragmentManager: FragmentManager, tabcount: Int) :
    FragmentPagerAdapter(fragmentManager) {

    var tabcount:Int?= tabcount


    override fun getItem(position: Int): Fragment {

        when(position)
        {
            0 -> {
                return NestedFirstFragment()
            }
            1 -> {
                return NestedThirdFragment()
            }
            2 -> {
                return NestedSecondFragment()
            }
            3 -> {
                return NestedThirdFragment()
            }
            else -> return NestedFirstFragment()
        }
    }

    override fun getCount(): Int {
        return tabcount!!
    }
}