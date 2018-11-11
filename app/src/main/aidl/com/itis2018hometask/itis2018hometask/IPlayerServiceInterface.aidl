package com.itis2018hometask.itis2018hometask;

import com.itis2018hometask.itis2018hometask.SongItem;

interface IPlayerServiceInterface {
    void addSongs(in List<SongItem> songs);
    void stop();
    void pause();
    void playSong(in int positions);
    void play();
    SongItem prev();
    SongItem next();
    boolean isPlaying();
    void seekTo(in int progress);
    int duration();
    int position();
}
