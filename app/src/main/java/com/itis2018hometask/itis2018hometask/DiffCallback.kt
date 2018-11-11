package com.itis2018hometask.itis2018hometask

import android.support.v7.util.DiffUtil

class DiffCallback : DiffUtil.ItemCallback<SongItem>() {
    override fun areItemsTheSame(oldItem: SongItem?, newItem: SongItem?): Boolean =
            oldItem?.name == newItem?.name

    override fun areContentsTheSame(oldItem: SongItem?, newItem: SongItem): Boolean =
            oldItem == newItem
}
