package com.itis2018hometask.itis2018hometask

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat

class SettingsFragment : PreferenceFragmentCompat() {

    fun newInstance(): SettingsFragment = SettingsFragment()

    override fun onCreatePreferences(p0: Bundle?, p1: String?) {
        addPreferencesFromResource(R.xml.app_preferences)
    }
}
