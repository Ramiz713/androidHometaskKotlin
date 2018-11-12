package com.itis2018hometask.itis2018hometask

import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceManager
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mService: IPlayerServiceInterface? = null

    private val mConnection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            mService = IPlayerServiceInterface.Stub.asInterface(service)
            mService?.addSongs(songList)
        }

        override fun onServiceDisconnected(className: ComponentName) {
            mService = null
        }
    }

    private val adapter = SongAdapter { position: Int, image: ImageView ->
        mService?.playSong(position)
        val serviceIntent = Intent(this, PlayerService::class.java)
        ContextCompat.startForegroundService(this, serviceIntent)
        val intent = Intent(this, PlayerActivity::class.java)
        intent.putExtra("Song item", songList.get(position))
        val transitionName = ViewCompat.getTransitionName(image)
        intent.putExtra("image", transitionName)
        val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, image, transitionName)
        startActivity(intent, optionsCompat.toBundle())
    }

    private lateinit var songList: ArrayList<SongItem>

    private val listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key -> recreate() }

    override fun onCreate(savedInstanceState: Bundle?) {
        PreferenceManager.setDefaultValues(this, R.xml.app_preferences, false)
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
        setContentView(R.layout.activity_main)
        songList = ArrayList<SongItem>()
        initList()
        rv_song.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_song.adapter = adapter
        adapter.submitList(songList)

        val serviceIntent = Intent(this, PlayerService::class.java)
        serviceIntent.action = IPlayerServiceInterface::class.java.name
        bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPref.unregisterOnSharedPreferenceChangeListener(listener)
    }

    private fun initList() {
        songList.add(SongItem("Get Lucky", "Daft Punk, Pharell Williams, Nile Rodgers", R.drawable.daftpunk, "Random Access Memories", R.raw.getlucky))
        songList.add(SongItem("Buzzcut Season", "Lorde", R.drawable.lorde, "Pure Heroine", R.raw.buzzcut_season))
        songList.add(SongItem("Комната", "Самое большое простое число", R.drawable.sbpch, "Мы не спали, мы снились", R.raw.sbpch))
        songList.add(SongItem("Ит", "Аигел", R.drawable.aigel, "Музыка", R.raw.aigel))
        songList.add(SongItem("Я сошла с ума", "t.A.T.u.", R.drawable.tatu, "200 По встречной", R.raw.tatu))
        songList.add(SongItem("Thunderclouds", "LSD, Sia, Diplo, Labrinth", R.drawable.lsd, "Thunderclouds", R.raw.thunderclouds))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return (when (item.itemId) {
            R.id.settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        })
    }
}
