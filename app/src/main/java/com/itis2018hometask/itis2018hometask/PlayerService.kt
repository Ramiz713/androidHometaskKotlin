package com.itis2018hometask.itis2018hometask

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class PlayerService : Service() {

    private var mediaPlayer = MediaPlayer()
    private var currentPosition: Int = 0
    private lateinit var songs: List<SongItem>

    override fun onBind(intent: Intent): IBinder = mBinder

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()
    }

    private val mBinder = object : IPlayerServiceInterface.Stub() {

        override fun addSongs(data: List<SongItem>) {
            songs = data
        }

        override fun stop() = mediaPlayer.stop()

        override fun pause() = mediaPlayer.pause()

        override fun playSong(position: Int) {
            currentPosition = position
            if (isPlaying) stop()
            mediaPlayer.reset()
            mediaPlayer = MediaPlayer.create(applicationContext, songs[position].source)
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener { next() }
        }

        override fun play() = mediaPlayer.start()

        override fun prev(): SongItem {
            if (--currentPosition < 0)
                currentPosition = songs.size - 1
            playSong(currentPosition)
            return songs[currentPosition]
        }

        override fun next(): SongItem {
            if (++currentPosition >= songs.size)
                currentPosition = 0
            playSong(currentPosition)
            return songs[currentPosition]
        }

        override fun position(): Int = mediaPlayer.currentPosition / 1000

        override fun duration(): Int = mediaPlayer.duration / 1000

        override fun isPlaying(): Boolean = mediaPlayer.isPlaying

        override fun seekTo(progress: Int) = mediaPlayer.seekTo(progress)
    }
}
