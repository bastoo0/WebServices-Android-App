package com.polytech.applicationws

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.polytech.applicationws.databinding.ActivityMainBinding
import com.polytech.applicationws.services.LoginPost
import kotlinx.coroutines.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("UserData", MODE_PRIVATE)
        val nomUtil: String? = prefs.getString("nomUtil", "")
        val motPasse: String? = prefs.getString("motPasse", "")
        binding.nomUtil.editText?.setText(nomUtil);
        binding.motPasse.editText?.setText(motPasse);

        binding.btValidate.setOnClickListener{ tryLogin() };


    }

    private fun tryLogin() {
        val isAllFieldsChecked = CheckAllFields();
        if (isAllFieldsChecked) {
            val sharedPref = getSharedPreferences("UserData", MODE_PRIVATE)
            val nomUtil: String = binding.nomUtil.editText?.text.toString();
            var motPasse: String = binding.motPasse.editText?.text.toString();

            with(sharedPref.edit()) {
                putString("nomUtil", nomUtil)
                putString("motPasse", motPasse)
                apply()
            }
            val api = APIClient.ApiUser.retrofitUserService
            val post = LoginPost(nomUtil, motPasse)
            val handler = CoroutineExceptionHandler { _, exception ->
                Toast.makeText(applicationContext, "Server error !", Toast.LENGTH_SHORT).show()
            }
            val coroutineScope = CoroutineScope(Dispatchers.Main)
            coroutineScope.launch(handler) {
                val resp = api.login(post)
                if (resp.isSuccessful) {
                    Toast.makeText(applicationContext, "Logged in !", Toast.LENGTH_SHORT).show()
                    val bearerToken : String = "Bearer " + resp.body()?.token
                    with(sharedPref.edit()) {
                        putString("token", bearerToken)
                        apply()
                    }
                    val lockListActivityIntent = Intent(applicationContext, MenuActivity::class.java)
                    startActivity(lockListActivityIntent)
                } else {
                    Toast.makeText(applicationContext, "Failed !", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun CheckAllFields(): Boolean {
        var bReturn = true
        if (binding.nomUtil.editText?.text.toString().isEmpty()) {
            binding.nomUtil.editText?.error = "Username field is required"
            bReturn = false
        }
        if (binding.motPasse.editText?.text.toString().isEmpty()) {
            binding.motPasse.editText?.error = "Password field is required"
            bReturn = false
        }

        return bReturn
    }
}
