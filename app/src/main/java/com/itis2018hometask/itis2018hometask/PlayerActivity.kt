package com.itis2018hometask.itis2018hometask

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : AppCompatActivity() {

    private var mService: IPlayerServiceInterface? = null

    private val mConnection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            mService = IPlayerServiceInterface.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(className: ComponentName) {
            mService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        val currentTheme = sharedPref.getString("current_theme", "")
        val themeId = when (currentTheme) {
            "1" -> R.style.LightTheme
            "2" -> R.style.GreenTheme
            "3" -> R.style.OrangeTheme
            else -> R.style.LightTheme
        }
        setTheme(themeId)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        supportActionBar?.hide()
        val songItem = intent.getParcelableExtra<SongItem>("Song item")
        song_image.setImageResource(songItem.image)
        song_name.text = songItem.name
        song_artist.text = songItem.artist
        song_album.text = "Album name : ${songItem.album}"
        song_image.transitionName = intent.getStringExtra("image")

        val serviceIntent = Intent(this, PlayerService::class.java)
        serviceIntent.action = IPlayerServiceInterface::class.java.name
        bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE)
        btn_play.setOnClickListener {
            val pauseBackground = if (currentTheme == "1") R.drawable.ic_pause_circle_filled_black_24dp
            else R.drawable.ic_pause_circle_filled_white_24dp
            val playBackground = if (currentTheme == "1") R.drawable.ic_play_circle_filled_black_24dp
            else R.drawable.ic_play_circle_filled_white_24dp
            if (mService?.isPlaying!!) {
                ContextCompat.startForegroundService(this, serviceIntent.setAction(PlayerService.ACTION_PAUSE))
                it.setBackgroundResource(playBackground)
            } else {
                ContextCompat.startForegroundService(this, serviceIntent.setAction(PlayerService.ACTION_PLAY))
                it.setBackgroundResource(pauseBackground)
            }
        }

        btn_next.setOnClickListener {
            updateViews(mService?.next())
            ContextCompat.startForegroundService(this, serviceIntent.setAction(PlayerService.ACTION_NEXT_FOR_PLAYER))
        }
        btn_prev.setOnClickListener {
            updateViews(mService?.prev())
            ContextCompat.startForegroundService(this, serviceIntent.setAction(PlayerService.ACTION_PREVIOUS_FOR_PLAYER))
        }
    }

    private fun updateViews(song: SongItem?) {
        song?.let {
            song_image.setImageResource(it.image)
            song_name.text = it.name
            song_artist.text = it.artist
            song_album.text = "Album name : ${it.album}"
            song_image.transitionName = it.name
        }
    }
}
