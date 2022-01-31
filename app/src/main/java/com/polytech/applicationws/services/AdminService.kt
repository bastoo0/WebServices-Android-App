package com.polytech.applicationws.services

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface AdminService {
    @GET("/user/{userid}")
    fun getProperties(@Path("userid") num : Int): Deferred<UserProperty>

}


