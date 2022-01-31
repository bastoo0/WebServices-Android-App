package com.polytech.applicationws

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.polytech.applicationws.databinding.FragmentLogsListBinding
import com.polytech.applicationws.viewmodels.LogsListAdapter
import com.polytech.applicationws.viewmodels.LogsListViewModel
import com.polytech.applicationws.viewmodels.LogsListViewModelFactory


class LogsListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLogsListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_logs_list, container, false
        )
        val application = requireNotNull(this.activity).application

        val prefs = application.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val token: String? = prefs?.getString("token", "")
        val viewModelFactory = LogsListViewModelFactory(application, token)

        var viewModel = ViewModelProvider(this,viewModelFactory).get(LogsListViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = LogsListAdapter()
        binding.logsList.adapter = adapter

        viewModel.response.observe(viewLifecycleOwner, Observer {
            it?.let {
                //adapter.data = it
                adapter.submitList(it)
            }
        })
        return binding.root
    }

    companion object {
        fun newInstance() = LogsListFragment()
    }
}