package com.manohar.ecommerceshoppingportal.model

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.manohar.ecommerceshoppingportal.view.blogFragment
import com.manohar.ecommerceshoppingportal.view.thirdfragment.NestedThirdFragment
import com.manohar.ecommerceshoppingportal.view.home.HomeFragment
import com.manohar.ecommerceshoppingportal.view.secondfrag.NestedSecondFragment

class TabAdapter(manager: FragmentManager): FragmentPagerAdapter(manager) {

    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitleList = ArrayList<String>()

    override fun getItem(position: Int): Fragment = mFragmentList[position]

    override fun getCount(): Int = mFragmentList.size

    fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

    fun addFragment(fragment: Fragment) {
        mFragmentList.add(fragment)
    }

    override fun getPageTitle(position: Int): CharSequence = mFragmentTitleList[position]

}