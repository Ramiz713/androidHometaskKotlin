package com.itis2018hometask.itis2018hometask

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.IBinder
import android.support.v4.app.NotificationCompat

class PlayerService : Service() {

    companion object {
        const val ACTION_PLAY = "action_play"
        const val ACTION_PAUSE = "action_pause"
        const val ACTION_NEXT = "action_next"
        const val ACTION_NEXT_FOR_PLAYER = "action_next_for_player"
        const val ACTION_PREVIOUS = "action_previous"
        const val ACTION_PREVIOUS_FOR_PLAYER = "action_previous_for_player"
    }

    private var mediaPlayer = MediaPlayer()
    private var currentPosition: Int = 0
    private lateinit var songs: List<SongItem>

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = when (intent?.action) {
            ACTION_NEXT -> createNotification(mBinder.next(), false)
            ACTION_NEXT_FOR_PLAYER, ACTION_PREVIOUS_FOR_PLAYER -> createNotification(songs[currentPosition], false)
            ACTION_PREVIOUS -> createNotification(mBinder.prev(), false)
            ACTION_PAUSE -> {
                mBinder.pause()
                createNotification(songs[currentPosition], true)
            }
            ACTION_PLAY -> {
                mBinder.play()
                createNotification(songs[currentPosition], false)
            }
            else -> createNotification(songs[currentPosition], false)
        }
        startForeground(1, notification)
        return START_NOT_STICKY
    }

    private fun createNotification(song: SongItem, isPause: Boolean): Notification {
        val pauseOrPlayAction = if (isPause) ACTION_PLAY else ACTION_PAUSE
        val image = if (isPause) R.drawable.ic_play_arrow_black_24dp else R.drawable.ic_pause_black_24dp

        val getIntent = { action: String -> Intent(this, PlayerService::class.java).setAction(action) }
        val getPendingIntent = { action: String -> PendingIntent.getService(this, 0, getIntent(action), 0) }

        val notification = NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(song.name)
                .setContentText(song.artist)
                .setLargeIcon(BitmapFactory.decodeResource(resources, song.image))
                .addAction(R.drawable.ic_skip_previous_black_24dp, "Previous", getPendingIntent(ACTION_PREVIOUS))
                .addAction(image, pauseOrPlayAction, getPendingIntent(pauseOrPlayAction))
                .addAction(R.drawable.ic_skip_next_black_24dp, "Next", getPendingIntent(ACTION_NEXT))
                .setStyle(android.support.v4.media.app.NotificationCompat.MediaStyle())
                .setSubText(song.album)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build()
        return notification
    }

    override fun onBind(intent: Intent): IBinder = mBinder

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()
        stopForeground(true)
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
