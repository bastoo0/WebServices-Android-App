package com.polytech.applicationws

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker
import com.polytech.applicationws.services.ActorPost
import java.util.*


class AddActorFragment : Fragment() {

    private lateinit var rootView: View
    var cal = Calendar.getInstance()
    var dateNaiss : Date? = null
    var dateDeces : Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_add_actor, container, false)

        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        rootView.findViewById<MaterialButton>(R.id.bt_date_birth).setOnClickListener {
            val dpd =
                context?.let { it1 ->
                    DatePickerDialog(it1, { view, year, monthOfYear, dayOfMonth ->
                        cal.set(Calendar.YEAR, year)
                        cal.set(Calendar.MONTH, monthOfYear)
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        var transMonth = Integer.toString(monthOfYear + 1)
                        if(monthOfYear < 10) transMonth = "0$transMonth"
                        this.dateNaiss = cal.time
                        rootView.findViewById<TextView>(R.id.tv_bd).text = "$dayOfMonth $transMonth $year"
                    }, year, month, day)
                }
            dpd?.show()
        }

        rootView.findViewById<MaterialButton>(R.id.bt_date_death).setOnClickListener {
            val dpd =
                context?.let { it1 ->
                    DatePickerDialog(it1, { view, year, monthOfYear, dayOfMonth ->
                        cal.set(Calendar.YEAR, year)
                        cal.set(Calendar.MONTH, monthOfYear)
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        this.dateDeces = cal.time
                        var transMonth = Integer.toString(monthOfYear + 1)
                        if(monthOfYear < 10) transMonth = "0$transMonth"
                        rootView.findViewById<TextView>(R.id.tv_dd).text = "$dayOfMonth $transMonth $year"
                    }, year, month, day)
                }
            dpd?.show()
        }

        rootView.findViewById<MaterialButton>(R.id.bt_validate_add_actor).setOnClickListener { tryAddActor() }
        return rootView
    }

    companion object {
        fun newInstance() = AddActorFragment()
    }

    private fun tryAddActor() {
        val nomAct = rootView.findViewById<TextInputLayout>(R.id.add_actor_lastName).editText?.text.toString()
        val prenAct = rootView.findViewById<TextInputLayout>(R.id.add_actor_firstName).editText?.text.toString()


        val api = APIClient.ApiUser.retrofitUserService
        val prefs = activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val post = ActorPost(nomAct, prenAct, dateNaiss, dateDeces)
        val token: String? = prefs?.getString("token", "")

        val coroutineScope = CoroutineScope(Dispatchers.Main)
        val handler = CoroutineExceptionHandler { _, exception ->
            print(exception)
            Toast.makeText(context, "Server error !", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack();
        }
        coroutineScope.launch(handler) {
            val resp = api.addActor(token, post)
            if(resp.isSuccessful) {
                Toast.makeText(context, "Success !", Toast.LENGTH_SHORT).show()
                val navController = findNavController()
                navController.run {
                    popBackStack()
                    popBackStack()
                    navigate(R.id.actorsFragment)
                }
            }
            else {
                Toast.makeText(context, "Failed !", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
