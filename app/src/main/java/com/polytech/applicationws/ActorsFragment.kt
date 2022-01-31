package com.polytech.applicationws

import android.app.AlertDialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.polytech.applicationws.databinding.FragmentActorsBinding
import com.polytech.applicationws.services.ActorProperty
import com.polytech.applicationws.services.UnlockDoorPost
import com.polytech.applicationws.viewmodels.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ActorsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentActorsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_actors, container, false
        )
        val application = requireNotNull(this.activity).application

        val prefs = application.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val token: String? = prefs?.getString("token", "")
        val viewModelFactory = ActorListViewModelFactory(application, token)

        var viewModel = ViewModelProvider(this,viewModelFactory).get(ActorListViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = ActorListAdapter(ActorListener { noAct ->
            val alertDialog: AlertDialog? = activity?.let {
                val builder = AlertDialog.Builder(it)
                builder.apply {
                    setPositiveButton(R.string.delete,
                        DialogInterface.OnClickListener { dialog, id ->
                            // User clicked OK button
                            deleteActor(noAct)
                        })
                    setNegativeButton(R.string.cancel,
                        DialogInterface.OnClickListener { dialog, id ->
                            // User cancelled the dialog
                        })
                }
                builder.create()
            }
            alertDialog?.setTitle("Supprimer acteur ?")
            alertDialog?.show()

        })
        binding.listActors.adapter = adapter

        viewModel.response.observe(viewLifecycleOwner, Observer {
            it?.let {
                //adapter.data = it
                adapter.submitList(it)
            }
        })
        binding.btAddActor.setOnClickListener { binding.root.findNavController().navigate(R.id.action_actorsFragment_to_addActorFragment) }
        return binding.root
    }

    fun deleteActor(noAct: Int?) {
        val api = APIClient.ApiUser.retrofitUserService

        val sharedPref = activity?.getSharedPreferences("UserData", MODE_PRIVATE)
        val token: String? = sharedPref?.getString("token", "")

        val handler = CoroutineExceptionHandler { _, exception ->
            println(exception.toString())
            Toast.makeText(activity, "Server error !", Toast.LENGTH_SHORT).show()
        }
        val coroutineScope = CoroutineScope(Dispatchers.Main)
        coroutineScope.launch(handler) {
            val resp = api.deleteActeur(token, noAct)
            if(resp.isSuccessful) {
                Toast.makeText(activity, "Deleted !", Toast.LENGTH_SHORT).show()
                val navController = findNavController()
                navController.run {
                    popBackStack()
                    navigate(R.id.actorsFragment)
                }
            }
            else {
                Toast.makeText(activity, "Failed !", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        fun newInstance() = ActorsFragment()
    }
}

class ActorListener(val clickListener: (noAct: Int?) -> Unit) {
    fun onClick(actor: ActorProperty) = clickListener(actor.noAct)
}