package com.polytech.applicationws

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.polytech.applicationws.services.AdminService
import com.polytech.applicationws.services.UserService
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

class APIClient {
     companion object {
         private val BASE_URL = "https://ws-polytech.loca.lt"

         private val interceptor = run {
             val httpLogInterceptor = HttpLoggingInterceptor()
             httpLogInterceptor.apply {
                 httpLogInterceptor.level = HttpLoggingInterceptor.Level.BODY
             }
         }

         private val okhttpCLient = OkHttpClient.Builder()
         .addInterceptor(interceptor)
         .build()

         private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory()).add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
            .build()

        val retrofit = Retrofit.Builder()
            .client(okhttpCLient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(BASE_URL)
            .build()
     }

     object ApiUser {
         val retrofitUserService : UserService by lazy { retrofit.create(UserService::class.java) }
     }

     object ApiAdmin {
         val retrofitAdminService : AdminService by lazy { retrofit.create(AdminService::class.java) }
     }

}
