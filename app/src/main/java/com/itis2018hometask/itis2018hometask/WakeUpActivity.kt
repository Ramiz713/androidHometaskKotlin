package com.itis2018hometask.itis2018hometask

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_wake_up.*

class WakeUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wake_up)
        gif_morning.settings.loadWithOverviewMode = true
        gif_morning.settings.useWideViewPort = true
        gif_morning.loadUrl("file:///android_asset/morning.gif")
    }
}
