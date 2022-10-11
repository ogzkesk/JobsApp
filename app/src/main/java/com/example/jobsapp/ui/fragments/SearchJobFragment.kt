package com.example.jobsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobsapp.R
import com.example.jobsapp.adapter.RemoteJobAdapter
import com.example.jobsapp.databinding.FragmentRemoteJobBinding
import com.example.jobsapp.databinding.FragmentSearchJobBinding
import com.example.jobsapp.models.Job
import com.example.jobsapp.ui.MainActivity
import com.example.jobsapp.util.Constants
import com.example.jobsapp.viewmodel.RemoteJobViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchJobFragment : Fragment() {
    private var _binding : FragmentSearchJobBinding? = null
    private val binding get() = _binding!!
    private lateinit var mViewModel : RemoteJobViewModel
    private lateinit var mAdapter : RemoteJobAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchJobBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel = (activity as MainActivity).remoteViewModel
        mAdapter = RemoteJobAdapter()
        binding.rvSearchJobs.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(object : DividerItemDecoration(requireContext(),LinearLayoutManager.VERTICAL) { })
            setHasFixedSize(true)
        }

        if(Constants.checkInternetConnection(requireContext())){
            searchJob()
        } else {
            Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT).show()
        }


    }

    private fun searchJob() {
        var job : kotlinx.coroutines.Job? = null
        binding.etSearch.addTextChangedListener { text ->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                text?.let {
                    if(text.toString().isNotEmpty()) {
                        mViewModel.searchJob(it.toString())
                        mViewModel.searchJobLivedata.observe(viewLifecycleOwner) { list ->
                            mAdapter.differ.submitList(list.jobs)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}