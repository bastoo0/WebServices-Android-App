package com.polytech.applicationws.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.polytech.applicationws.APIClient
import com.polytech.applicationws.services.FilmProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FilmListViewModel(token: String?) : ViewModel() {

    private val _response = MutableLiveData<List<FilmProperty>>()

    val response: LiveData<List<FilmProperty>>
        get() = _response

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        getFilmsProperties(token)
    }

    private fun getFilmsProperties(token: String?) {
        coroutineScope.launch {
            var getFilmsDeferred = APIClient.ApiUser.retrofitUserService.getFilms(token)
            try {
                var listResult = getFilmsDeferred.await()
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