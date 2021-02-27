package com.manohar.ecommerceshoppingportal.ui.nestedHomeFragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.manohar.ecommerceshoppingportal.ui.nestedHomeFragments.firstfragment.NestedFirstFragment
import com.manohar.ecommerceshoppingportal.ui.nestedHomeFragments.thirdfragment.NestedThirdFragment
import com.manohar.ecommerceshoppingportal.ui.nestedHomeFragments.secondfrag.NestedSecondFragment

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