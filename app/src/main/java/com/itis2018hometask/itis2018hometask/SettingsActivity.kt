package com.itis2018hometask.itis2018hometask

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceManager

class SettingsActivity : AppCompatActivity() {

    private val listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key -> recreate() }

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPref.registerOnSharedPreferenceChangeListener(listener)
        val currentTheme = sharedPref.getString("current_theme", "")
        val themeId = when (currentTheme) {
            "1" -> R.style.LightTheme
            "2" -> R.style.GreenTheme
            "3" -> R.style.OrangeTheme
            else -> R.style.LightTheme
        }
        setTheme(themeId)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_main, SettingsFragment().newInstance())
                .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPref.unregisterOnSharedPreferenceChangeListener(listener)
    }
}
