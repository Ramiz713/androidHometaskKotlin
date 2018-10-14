package com.itis2018hometask.itis2018hometask

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFragment(FragmentEmpty.newInstance("Первый фрагмент"))
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                initFragment(FragmentEmpty.newInstance("Первый фрагмент"))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                initFragment(FragmentPeople().newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                initFragment(FragmentViewPager().newInstance())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun initFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_main, fragment)
                .commit()
    }
}
