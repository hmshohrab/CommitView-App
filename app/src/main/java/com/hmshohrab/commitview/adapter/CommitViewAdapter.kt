package com.hmshohrab.commitview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.hmshohrab.commitview.databinding.ItemCommitBinding
import com.hmshohrab.commitview.repository.model.CommitModelItem
import com.hmshohrab.commitview.utils.SimpleCallback
import com.hmshohrab.commitview.utils.TimeUtils
import javax.inject.Inject

class CommitViewAdapter @Inject constructor() :
    PagingDataAdapter<CommitModelItem, CommitViewAdapter.ProductViewHolder>(Comparator) {

    var clickListener: SimpleCallback<CommitModelItem>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layOutBinding =
            ItemCommitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(layOutBinding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        getItem(position)?.let {
             val inputFormat = "yyyy-MM-dd'T'HH:mm:ss"
            val outputFormat = "MMM dd, yyyy"
            val formattedDate: String? =
                it.commit?.author?.date?.let { it1 -> TimeUtils.formatDateFromString(it1, inputFormat, outputFormat) }
            holder.binding.txtCommitValue.text = it.commit?.message
            holder.binding.dateTime.text = formattedDate
            holder.binding.authorName.text = it.commit?.author?.name

            holder.binding.imgProfile.load(it.committer?.avatar_url)
            holder.binding.root.setOnClickListener { view -> clickListener?.callback(it) }


        }

    }

    inner class ProductViewHolder(val binding: ItemCommitBinding) : ViewHolder(binding.root) {
    }

    companion object {
        private val Comparator =
            object : DiffUtil.ItemCallback<CommitModelItem>() {
                override fun areItemsTheSame(
                    oldItem: CommitModelItem,
                    newItem: CommitModelItem
                ): Boolean {
                    return oldItem.node_id == newItem.node_id
                }

                override fun areContentsTheSame(
                    oldItem: CommitModelItem,
                    newItem: CommitModelItem
                ): Boolean {
                    return oldItem == newItem
                }

            }

    }
}