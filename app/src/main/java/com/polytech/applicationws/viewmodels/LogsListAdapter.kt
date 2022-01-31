package com.polytech.applicationws.viewmodels

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.polytech.applicationws.databinding.ItemLogsListBinding
import com.polytech.applicationws.services.LogProperty
import java.util.*

class LogsListAdapter() : ListAdapter<LogProperty, LogsListAdapter.ViewHolder>(
    LogsPropertyDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class LogsPropertyDiffCallback : DiffUtil.ItemCallback<LogProperty>() {
        override fun areItemsTheSame(oldItem: LogProperty, newItem: LogProperty): Boolean {
            return oldItem.MAC == newItem.MAC
        }

        override fun areContentsTheSame(oldItem: LogProperty, newItem: LogProperty): Boolean {
            return oldItem == newItem
        }
    }
    class ViewHolder private constructor(val binding: ItemLogsListBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: LogProperty) {
            binding.log = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemLogsListBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}