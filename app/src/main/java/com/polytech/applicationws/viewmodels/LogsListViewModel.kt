package com.polytech.applicationws.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.polytech.applicationws.APIClient
import com.polytech.applicationws.services.LogProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LogsListViewModel(token: String?) : ViewModel() {

    private val _response = MutableLiveData<List<LogProperty>>()

    val response: LiveData<List<LogProperty>>
        get() = _response

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        getLogProperties(token)
    }

    private fun getLogProperties(token: String?) {
        coroutineScope.launch {
            var getLogsDeferred = APIClient.ApiUser.retrofitUserService.getLogs(token)
            try {
                var listResult = getLogsDeferred.await()
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