package com.itis2018hometask.itis2018hometask

import android.support.v7.util.DiffUtil

class DiffCallback : DiffUtil.ItemCallback<PeopleItem>() {
    override fun areItemsTheSame(oldItem: PeopleItem?, newItem: PeopleItem?): Boolean =
            oldItem?.name == newItem?.name

    override fun areContentsTheSame(oldItem: PeopleItem?, newItem: PeopleItem): Boolean =
            oldItem == newItem
}