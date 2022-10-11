package com.example.jobsapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobsapp.models.FavoriteJob
import com.example.jobsapp.repository.JobRepository
import kotlinx.coroutines.launch

class RemoteJobViewModel(application: Application, private val repository: JobRepository) :
    AndroidViewModel(application) {

    val remoteJobLiveData = repository.remoteJobResult()
    val searchJobLivedata = repository.searchJobResult()

    fun searchJob(query: String?) = repository.searchJobResponse(query)


    fun addFavJob(job: FavoriteJob) = viewModelScope.launch {
        repository.addFavoriteJob(job)
    }

    fun deleteFavJob(job: FavoriteJob) = viewModelScope.launch {
        repository.deleteFavoriteJob(job)
    }

    fun getAllFavJobs() = repository.getAllFavJobs()
}