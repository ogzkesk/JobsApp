package com.example.jobsapp.api

import com.example.jobsapp.models.RemoteJobResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteApi {


    @GET("remote-jobs")
    fun getRemoteJobResponse() : Call<RemoteJobResponse>

    @GET("remote-jobs")
    fun searchJob(
        @Query("search") search : String?
    ) : Call<RemoteJobResponse>
}