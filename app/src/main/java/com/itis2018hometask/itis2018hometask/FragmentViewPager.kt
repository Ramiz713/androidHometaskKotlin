package com.itis2018hometask.itis2018hometask

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class FragmentViewPager : Fragment() {

    fun newInstance(): FragmentViewPager = FragmentViewPager()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)
        val viewPager = view.findViewById<ViewPager>(R.id.view_pager)
        setViewPager(viewPager)
        val tabsView = view.findViewById<TabLayout>(R.id.tabs)
        tabsView.setupWithViewPager(viewPager)
        return view
    }

    private fun setViewPager(pager: ViewPager?) {
        val adapter = FragmentsViewPagerAdapter(childFragmentManager)
        val fragmentOne = FragmentEmpty.newInstance("First Fragment")
        val fragmentTwo = FragmentEmpty.newInstance("Second Fragment")
        val fragmentThree = FragmentEmpty.newInstance("Third Fragment")
        adapter.addFragment(fragmentOne, "First")
        adapter.addFragment(fragmentTwo, "Second")
        adapter.addFragment(fragmentThree, "Third")
        pager?.adapter = adapter
    }

    private class FragmentsViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

        val fragmentList = ArrayList<Fragment>()
        val fragmentTitlesList = ArrayList<String>()

        override fun getItem(position: Int): Fragment = fragmentList.get(position)
        override fun getCount(): Int = fragmentList.size
        override fun getPageTitle(position: Int): CharSequence? = fragmentTitlesList.get(position)

        fun addFragment(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            fragmentTitlesList.add(title)
        }
    }
}
