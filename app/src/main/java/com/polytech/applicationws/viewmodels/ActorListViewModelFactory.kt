package com.polytech.applicationws.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ActorListViewModelFactory (
    private val application: Application,
    private val token: String?
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ActorListViewModel::class.java)) {
            return ActorListViewModel(token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}