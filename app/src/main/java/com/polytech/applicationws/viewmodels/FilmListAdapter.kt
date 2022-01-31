package com.polytech.applicationws.viewmodels

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.polytech.applicationws.databinding.ItemMenuBinding
import com.polytech.applicationws.services.FilmProperty

class FilmListAdapter() : ListAdapter<FilmProperty, FilmListAdapter.ViewHolder>(
    FilmPropertyDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //val item = getItem(position)
        //holder.bind(item)
        holder.bind(getItem(position))
    }

    class FilmPropertyDiffCallback : DiffUtil.ItemCallback<FilmProperty>() {
        override fun areItemsTheSame(oldItem: FilmProperty, newItem: FilmProperty): Boolean {
            return oldItem.noFilm == newItem.noFilm
        }

        override fun areContentsTheSame(oldItem: FilmProperty, newItem: FilmProperty): Boolean {
            return oldItem == newItem
        }
    }
    class ViewHolder private constructor(val binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: FilmProperty) {
            binding.film = item
            println(item)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMenuBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    /*class LockListListener(val clickListener: (lockMAC: String?) -> Unit) {
        fun onClick(film: FilmProperty) = clickListener(film.MAC)
    }*/
}