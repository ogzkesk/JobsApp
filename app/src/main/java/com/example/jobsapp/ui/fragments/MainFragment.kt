package com.example.jobsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jobsapp.R
import com.example.jobsapp.databinding.FragmentDetailsBinding
import com.example.jobsapp.databinding.FragmentMainBinding
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems

class MainFragment : Fragment() {
    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTabBar()

    }

    private fun setupTabBar() {
        val adapter = FragmentPagerItemAdapter(childFragmentManager, FragmentPagerItems.with(requireContext())
            .add("Jobs",RemoteJobFragment::class.java)
            .add("Search",SearchJobFragment::class.java)
            .add("Saved Jobs",SavedJobFragment::class.java)
            .create()
        )

        binding.viewpager.adapter = adapter
        binding.viewpagerTab.setViewPager(binding.viewpager)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}