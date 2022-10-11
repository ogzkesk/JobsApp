package com.example.jobsapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.jobsapp.R
import com.example.jobsapp.databinding.FragmentDetailsBinding
import com.example.jobsapp.models.FavoriteJob
import com.example.jobsapp.models.Job
import com.example.jobsapp.ui.MainActivity
import com.example.jobsapp.viewmodel.RemoteJobViewModel
import com.google.android.material.snackbar.Snackbar


class DetailsFragment : Fragment() {
    private var _binding : FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var currentJob : Job
    private lateinit var viewModel : RemoteJobViewModel
    private val args by navArgs<DetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).remoteViewModel
        args.job?.let {
            currentJob = it
        }
        setupWebView()
        binding.fabAddFavorite.setOnClickListener {
            addFavJob(it)
        }


    }

    private fun addFavJob(view : View) {
        val favJob = FavoriteJob(
            0,
            currentJob.candidateRequiredLocation,
            currentJob.category,
            currentJob.companyLogoUrl,
            currentJob.companyName,
            currentJob.description,
            currentJob.id,
            currentJob.jobType,
            currentJob.publicationDate,
            currentJob.salary,
            currentJob.title,
            currentJob.url
        )

        viewModel.addFavJob(favJob)
        Snackbar.make(view,"Job saved successfully",Snackbar.LENGTH_LONG).show()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        binding.webView.apply {
            webViewClient = WebViewClient()
            currentJob.url?.let { loadUrl(it) }

        }

        val settings = binding.webView.settings
        settings.javaScriptEnabled = true
        settings.setAppCacheEnabled(true)
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.setSupportZoom(false)
        settings.builtInZoomControls = false
        settings.displayZoomControls = false
        settings.textZoom = 100
        settings.blockNetworkImage = false
        settings.loadsImagesAutomatically = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}