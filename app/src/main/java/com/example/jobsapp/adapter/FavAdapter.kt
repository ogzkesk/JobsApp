package com.example.jobsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jobsapp.databinding.JobLayoutAdapterBinding
import com.example.jobsapp.models.FavoriteJob
import com.example.jobsapp.models.Job
import com.example.jobsapp.ui.fragments.MainFragmentDirections

class FavAdapter(private val onClick : OnItemClickListener) : RecyclerView.Adapter<FavAdapter.FavViewHolder>() {

    class FavViewHolder(val binding: JobLayoutAdapterBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallBack = object : DiffUtil.ItemCallback<FavoriteJob>() {
        override fun areItemsTheSame(oldItem: FavoriteJob, newItem: FavoriteJob): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavoriteJob, newItem: FavoriteJob): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        return FavViewHolder(
            JobLayoutAdapterBinding
            .inflate(
                LayoutInflater
                .from(parent.context),
                parent,false))
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val currentItem = differ.currentList[position]

        holder.itemView.apply {
            Glide.with(this)
                .load(currentItem.companyLogoUrl)
                .into(holder.binding.ivCompanyLogo)

            holder.binding.tvCompanyName.text = currentItem.companyName
            holder.binding.tvJobLocation.text = currentItem.candidateRequiredLocation
            holder.binding.tvJobTitle.text    = currentItem.title
            holder.binding.tvJobType.text     = currentItem.jobType
            holder.binding.ibDelete.visibility = View.VISIBLE


            val dateJob = currentItem.publicationDate?.split("T")
            holder.binding.tvDate.text = dateJob?.get(0)
        }.setOnClickListener { mView ->

            val tags = arrayListOf<String>()
            val job = Job(
                currentItem.candidateRequiredLocation,
                currentItem.category,
                "",
                currentItem.companyLogoUrl,
                currentItem.companyName,
                currentItem.description,
                currentItem.jobId,
                currentItem.jobType,
                currentItem.publicationDate,
                currentItem.salary,
                tags,
                currentItem.title,
                currentItem.url,
            )
            val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(job)
            mView.findNavController().navigate(action)
        }

        holder.itemView.apply {
            holder.binding.ibDelete.setOnClickListener{
                onClick.onItemClick(currentItem,holder.binding.ibDelete,position)
            }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    interface OnItemClickListener {
        fun onItemClick(
            job : FavoriteJob,
            view : View,
            position: Int
        )
    }

}