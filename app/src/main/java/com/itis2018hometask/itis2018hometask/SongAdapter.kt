package com.itis2018hometask.itis2018hometask

import android.support.v4.view.ViewCompat
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.song_item.view.*

class SongAdapter(val listener: (Int, ImageView) -> Unit) : ListAdapter<SongItem, SongAdapter.SongHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHolder {
        return SongHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.song_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SongAdapter.SongHolder, position: Int) {
        holder.bind(getItem(position), listener)
        ViewCompat.setTransitionName(holder.itemView.song_image, holder.itemView.song_name.text.toString())
    }

    override fun submitList(list: List<SongItem>?) {
        super.submitList(if (list != null) ArrayList<SongItem>(list) else null)
    }

    class SongHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: SongItem, listener: (Int, ImageView) -> Unit) = with(itemView) {
            song_name.text = item.name
            song_artist.text = item.artist
            Glide.with(this).load(item.image).into(song_image)
            setOnClickListener {
                listener(adapterPosition, song_image)
            }
        }
    }
}
