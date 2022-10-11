package com.example.jobsapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jobsapp.repository.JobRepository

class RemoteJobViewModelFactory(
    private val application: Application,
    private val repository: JobRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RemoteJobViewModel(application, repository) as T
    }
}