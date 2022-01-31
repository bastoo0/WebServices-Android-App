package com.polytech.applicationws.viewmodels

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.polytech.applicationws.ActorListener
import com.polytech.applicationws.databinding.ItemActorsBinding
import com.polytech.applicationws.services.ActorProperty

class ActorListAdapter(val clickListener: ActorListener) : ListAdapter<ActorProperty, ActorListAdapter.ViewHolder>(
    ActorPropertyDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //val item = getItem(position)
        //holder.bind(item)
        holder.bind(getItem(position)!!, clickListener)
    }

    class ActorPropertyDiffCallback : DiffUtil.ItemCallback<ActorProperty>() {
        override fun areItemsTheSame(oldItem: ActorProperty, newItem: ActorProperty): Boolean {
            return oldItem.noAct == newItem.noAct
        }

        override fun areContentsTheSame(oldItem: ActorProperty, newItem: ActorProperty): Boolean {
            return oldItem == newItem
        }
    }
    class ViewHolder private constructor(val binding: ItemActorsBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: ActorProperty, clickListener: ActorListener) {
            binding.actor = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemActorsBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}