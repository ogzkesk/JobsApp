package com.example.jobsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.jobsapp.databinding.ActivityMainBinding
import com.example.jobsapp.db.FavoriteJobDatabase
import com.example.jobsapp.repository.JobRepository
import com.example.jobsapp.viewmodel.RemoteJobViewModel
import com.example.jobsapp.viewmodel.RemoteJobViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    lateinit var remoteViewModel : RemoteJobViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = ""


        setupViewModel()

    }

    private fun setupViewModel() {
        val db = FavoriteJobDatabase.getDatabase(applicationContext)
        val repository = JobRepository(db)
        val viewModelFactory = RemoteJobViewModelFactory(
            application,repository
        )

        remoteViewModel = ViewModelProvider(this,viewModelFactory)[RemoteJobViewModel::class.java]
    }
}