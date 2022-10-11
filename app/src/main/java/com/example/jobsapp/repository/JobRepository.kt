package com.example.jobsapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jobsapp.api.RetrofitInstance
import com.example.jobsapp.db.FavoriteJobDatabase
import com.example.jobsapp.models.FavoriteJob
import com.example.jobsapp.models.RemoteJobResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JobRepository(private val db: FavoriteJobDatabase) {

    private val remoteJobService = RetrofitInstance.apiService
    private val remoteJobResponseLiveData : MutableLiveData<RemoteJobResponse> = MutableLiveData()
    private val searchJobResponseLiveData : MutableLiveData<RemoteJobResponse> = MutableLiveData()

    init {
        getRemoteJobResponse()
    }

    private fun getRemoteJobResponse() {
        remoteJobService.getRemoteJobResponse().enqueue(object : Callback<RemoteJobResponse> {
            override fun onResponse(
                call: Call<RemoteJobResponse>,
                response: Response<RemoteJobResponse>
            ) {
                remoteJobResponseLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<RemoteJobResponse>, t: Throwable) {
                Log.d("RESPONSE FAIL", "onResponse: ${t.message}")
            }
        })
    }

    fun searchJobResponse(query : String?) {
        remoteJobService.searchJob(query).enqueue(object : Callback<RemoteJobResponse>{
            override fun onResponse(
                call: Call<RemoteJobResponse>,
                response: Response<RemoteJobResponse>
            ) {
                searchJobResponseLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<RemoteJobResponse>, t: Throwable) {
                Log.d("RESPONSE SEARCH FAILURE", "onFailure: ${t.message} ")
            }
        })
    }

    fun searchJobResult() : LiveData<RemoteJobResponse> {
        return searchJobResponseLiveData
    }

    fun remoteJobResult() : LiveData<RemoteJobResponse> {
        return remoteJobResponseLiveData
    }

    /** DB */
    suspend fun addFavoriteJob(job: FavoriteJob) = db.favDao().addFavoriteJob(job)
    suspend fun deleteFavoriteJob(job: FavoriteJob) = db.favDao().deleteFavJob(job)
    fun getAllFavJobs() = db.favDao().getAllFavJob()

}