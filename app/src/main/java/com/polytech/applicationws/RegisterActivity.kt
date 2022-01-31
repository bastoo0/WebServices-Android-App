package com.polytech.applicationws

import android.R
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.polytech.applicationws.databinding.ActivityRegisterBinding
import com.polytech.applicationws.services.RegisterPost
import kotlinx.coroutines.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import android.widget.ArrayAdapter

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val countriesViewModel = CountryViewModel()
        val listCountries : ArrayList<Country> = ArrayList()
        listCountries.add(Country("France"))
        listCountries.add(Country("USA"))
        listCountries.add(Country("Germany"))
        listCountries.add(Country("Japan"))
        listCountries.add(Country("Belgium"))

        val adapter: ArrayAdapter<Country> = ArrayAdapter<Country>(this, R.layout.simple_expandable_list_item_1, listCountries)
        binding.regCountry.setAdapter(adapter)
        countriesViewModel.countryList = listCountries
        binding.viewmodel = countriesViewModel
        binding.btRegCreateAccount.setOnClickListener{ tryRegister() };
    }

    private fun tryRegister() {
        val isAllFieldsChecked = CheckAllFields();
        if (isAllFieldsChecked) {
            val sharedPref = getSharedPreferences("UserData", MODE_PRIVATE)
            val email: String = binding.regEmail.editText?.text.toString();
            var pwd: String = binding.regPassword.editText?.text.toString();
            var firstName: String = binding.regFirstName.editText?.text.toString();
            var lastName: String = binding.regLastName.editText?.text.toString();
            var age: Int = Integer.parseInt(binding.regAge.editText?.text.toString());
            var country: String? = binding.viewmodel?.countryIdValue.toString();

            val api = APIClient.ApiUser.retrofitUserService
            val post = RegisterPost(email, pwd, firstName, lastName, age, country)
            val handler = CoroutineExceptionHandler { _, exception ->
                print(exception)
                Toast.makeText(applicationContext, "Server error !", Toast.LENGTH_SHORT).show()
            }
            val coroutineScope = CoroutineScope(Dispatchers.Main)
            /*coroutineScope.launch(handler) {
                val resp = api.register(post)
                if (resp.isSuccessful) {
                    with(sharedPref.edit()) {
                        putString("email", email)
                        putString("password", pwd)
                        apply()
                    }
                    Toast.makeText(applicationContext, "Registered, please login !", Toast.LENGTH_LONG)
                        .show()
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Failed !", Toast.LENGTH_SHORT).show()
                }
            }*/
        }
        else {
            Toast.makeText(applicationContext, "Missing fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun CheckAllFields(): Boolean {
        var bReturn = true
        if (binding.regEmail.editText?.text.toString().isEmpty()) {
            binding.regEmail.editText?.error = "Email field is required"
            bReturn = false
        }
        if (binding.regPassword.editText?.text.toString().isEmpty()) {
            binding.regPassword.editText?.error = "Password field is required"
            bReturn = false
        }
        if (binding.regFirstName.editText?.text.toString().isEmpty()) {
            binding.regFirstName.editText?.error = "First Name field is required"
            bReturn = false
        }
        if (binding.regLastName.editText?.text.toString().isEmpty()) {
            binding.regLastName.editText?.error = "Last Name field is required"
            bReturn = false
        }
        if (binding.regAge.editText?.text.toString().isEmpty()) {
            binding.regAge.editText?.error = "Age field is required"
            bReturn = false
        }
        return bReturn
    }
}

class CountryViewModel() : ViewModel() {
    val countryIdItemPosition = MutableLiveData<Int>()

    var countryIdValue
        get() =
            countryIdItemPosition.value?.let {
                countryList?.get(it)?.id
            }
        set(value) {
            val position = countryList?.indexOfFirst {
                it.id == value
            } ?: -1
            if (position != -1) {
                countryIdItemPosition.value = position + 1
            }
        }
    val countryIdItem
        get() =
            countryIdItemPosition.value?.let {
                countryList?.get(it)
            }

    var countryList: List<Country>? = null

}

class Country(var id: String) {
    override fun toString() : String = id
}


