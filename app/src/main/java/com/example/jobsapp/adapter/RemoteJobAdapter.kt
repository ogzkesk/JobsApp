package com.example.jobsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jobsapp.databinding.JobLayoutAdapterBinding
import com.example.jobsapp.models.Job
import com.example.jobsapp.ui.fragments.MainFragmentDirections

class RemoteJobAdapter : RecyclerView.Adapter<RemoteJobAdapter.RemoteViewHolder>() {

    class RemoteViewHolder(val binding: JobLayoutAdapterBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallBack = object : DiffUtil.ItemCallback<Job>() {
        override fun areItemsTheSame(oldItem: Job, newItem: Job): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Job, newItem: Job): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemoteViewHolder {
         return RemoteViewHolder(JobLayoutAdapterBinding
        .inflate(LayoutInflater
        .from(parent.context),
        parent,false))
    }

    override fun onBindViewHolder(holder: RemoteViewHolder, position: Int) {
        val currentItem = differ.currentList[position]

        holder.itemView.apply {
            Glide.with(this)
                .load(currentItem.companyLogoUrl)
                .into(holder.binding.ivCompanyLogo)

            holder.binding.tvCompanyName.text = currentItem.companyName
            holder.binding.tvJobLocation.text = currentItem.candidateRequiredLocation
            holder.binding.tvJobTitle.text    = currentItem.title
            holder.binding.tvJobType.text     = currentItem.jobType

            val dateJob = currentItem.publicationDate?.split("T")
            holder.binding.tvDate.text = dateJob?.get(0)
        }.setOnClickListener { mView ->
            val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(currentItem)
            mView.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}
