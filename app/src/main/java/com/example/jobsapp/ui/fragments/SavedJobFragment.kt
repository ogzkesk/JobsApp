package com.example.jobsapp.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobsapp.R
import com.example.jobsapp.adapter.FavAdapter
import com.example.jobsapp.databinding.FragmentRemoteJobBinding
import com.example.jobsapp.databinding.FragmentSaveJobBinding
import com.example.jobsapp.models.FavoriteJob
import com.example.jobsapp.ui.MainActivity
import com.example.jobsapp.viewmodel.RemoteJobViewModel


class SavedJobFragment : Fragment(), FavAdapter.OnItemClickListener {
    private var _binding : FragmentSaveJobBinding? = null
    private val binding get() = _binding!!
    private lateinit var mFavAdapter : FavAdapter
    private lateinit var mViewModel : RemoteJobViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSaveJobBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel = (activity as MainActivity).remoteViewModel
        mFavAdapter = FavAdapter(this)

        binding.rvJobsSaved.apply {
            adapter = mFavAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            addItemDecoration(object : DividerItemDecoration(requireContext(),LinearLayoutManager.VERTICAL) { })
        }

        mViewModel.getAllFavJobs().observe(viewLifecycleOwner){ list ->

            list?.let {
                mFavAdapter.differ.submitList(it)
                updateUI(it)
            }

        }
    }

    private fun updateUI(list: List<FavoriteJob>) {
        if(list.isNotEmpty()) {
            binding.rvJobsSaved.visibility = View.VISIBLE
            binding.cardNoAvailable.visibility = View.GONE
        } else {
            binding.rvJobsSaved.visibility = View.GONE
            binding.cardNoAvailable.visibility = View.VISIBLE
        }
    }

    override fun onItemClick(job: FavoriteJob, view: View, position: Int) {

        deleteJob(job)



    }

    private fun deleteJob(job: FavoriteJob) {
        AlertDialog.Builder(requireContext())
            .setTitle("Are you sure?")
            .setMessage("Are you sure permanently delete this job?")
            .setPositiveButton("DELETE") {_,_->
                mViewModel.deleteFavJob(job)
                Toast.makeText(requireContext(), "Job deleted", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("CANCEL",null)
            .create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}