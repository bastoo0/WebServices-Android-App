package com.polytech.applicationws

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.polytech.applicationws.databinding.FragmentMenuBinding
import com.polytech.applicationws.services.ActorProperty
import com.polytech.applicationws.viewmodels.FilmListAdapter
import com.polytech.applicationws.viewmodels.FilmListViewModel
import com.polytech.applicationws.viewmodels.FilmListViewModelFactory
import com.polytech.applicationws.services.UnlockDoorPost
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMenuBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_menu, container, false
        )
        val application = requireNotNull(this.activity).application

        val prefs = application.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val token: String? = prefs?.getString("token", "")
        val viewModelFactory = FilmListViewModelFactory(application, token)

        var viewModel = ViewModelProvider(this,viewModelFactory).get(FilmListViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = FilmListAdapter()
        binding.listFilms.adapter = adapter

        viewModel.response.observe(viewLifecycleOwner, Observer {
            it?.let {
                //adapter.data = it
                adapter.submitList(it)
            }
        })
        binding.btActors.setOnClickListener { binding.root.findNavController().navigate(R.id.action_menuFragment_to_actorsFragment) }
        return binding.root
    }

    companion object {
        fun newInstance() = MenuFragment()
    }
}