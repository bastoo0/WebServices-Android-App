package com.polytech.applicationws.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.polytech.applicationws.APIClient
import com.polytech.applicationws.services.ActorProperty
import com.polytech.applicationws.services.FilmProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ActorListViewModel(token: String?) : ViewModel() {

    private val _response = MutableLiveData<List<ActorProperty>>()

    val response: LiveData<List<ActorProperty>>
        get() = _response

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        getActorsProperties(token)
    }

    private fun getActorsProperties(token: String?) {
        coroutineScope.launch {
            var getActorsDeferred = APIClient.ApiUser.retrofitUserService.getActors(token)
            try {
                var listResult = getActorsDeferred.await()
                _response.value = listResult
            } catch (e: Exception) {
                println(e)
                _response.value = ArrayList()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}