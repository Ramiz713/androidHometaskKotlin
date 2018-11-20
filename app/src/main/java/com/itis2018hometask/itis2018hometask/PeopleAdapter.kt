package com.itis2018hometask.itis2018hometask

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.people_item.view.*

class PeopleAdapter : ListAdapter<PeopleItem, PeopleAdapter.PeopleHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleHolder =
            PeopleHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.people_item, parent, false))

    override fun onBindViewHolder(holder: PeopleAdapter.PeopleHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: List<PeopleItem>?) =
            super.submitList(if (list != null) ArrayList<PeopleItem>(list) else null)

    class PeopleHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: PeopleItem) = with(itemView) {
            person_name.text = item.name
            person_points.text = "Points: ${item.points}"
            Glide.with(this).load(item.image).into(person_image)
        }
    }
}
