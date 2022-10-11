package com.example.jobsapp.ui.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.view.menu.MenuAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.jobsapp.R
import com.example.jobsapp.adapter.RemoteJobAdapter
import com.example.jobsapp.databinding.FragmentDetailsBinding
import com.example.jobsapp.databinding.FragmentRemoteJobBinding
import com.example.jobsapp.ui.MainActivity
import com.example.jobsapp.util.Constants
import com.example.jobsapp.viewmodel.RemoteJobViewModel

class RemoteJobFragment : Fragment(),SwipeRefreshLayout.OnRefreshListener{


    private var _binding : FragmentRemoteJobBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : RemoteJobViewModel
    private lateinit var mAdapter : RemoteJobAdapter
    private lateinit var swipeLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRemoteJobBinding.inflate(inflater,container,false)

        swipeLayout = binding.swipeContainer
        swipeLayout.setOnRefreshListener(this)
        swipeLayout.setColorSchemeColors(
            Color.GREEN, Color.RED,
            Color.BLUE, Color.CYAN
        )

        swipeLayout.post {
            swipeLayout.isRefreshing = true
            setupRecyclerView()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).remoteViewModel
        setupRecyclerView()

        binding.swipeContainer.setOnRefreshListener {
            fetchingData()
        }

    }

    override fun onRefresh() {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        mAdapter = RemoteJobAdapter()
        binding.rvRemoteJobs.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            addItemDecoration(object : DividerItemDecoration(requireContext(),LinearLayoutManager.VERTICAL){ })
            adapter = mAdapter
        }

        fetchingData()
    }

    private fun fetchingData() {
        if(Constants.checkInternetConnection(requireContext())) {
            viewModel.remoteJobLiveData.observe(viewLifecycleOwner) { remoteJob ->
                if(remoteJob != null) {
                    mAdapter.differ.submitList(remoteJob.jobs)
                    swipeLayout.isRefreshing = false
                }
            }
        } else {
            Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT).show()
            swipeLayout.isRefreshing = false
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}