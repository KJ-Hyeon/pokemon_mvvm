package com.example.pokebook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokebook.data.model.RecentSearches
import com.example.pokebook.databinding.ItemSearchBinding

class SearchAdapter: RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private var searchList = mutableListOf<RecentSearches>()
    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(recentSearches: RecentSearches)
        fun onDeleteClick(recentSearches: RecentSearches, position: Int)
    }

    fun setOnItemClick(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(searchList[position])
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    fun addSearch(searches: String) {

    }

    fun setSearchList(recentSearches: List<RecentSearches>) {
        searchList = recentSearches.toMutableList()
        if (searchList.size == 0) {
            notifyItemRemoved(0)
        } else {
            notifyItemRangeChanged(0, searchList.size)
        }
    }

    inner class SearchViewHolder(private val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecentSearches) {
            binding.search = item

            binding.root.setOnClickListener {
                listener?.onItemClick(item)
            }
            binding.cancelButton.setOnClickListener {
                listener?.onDeleteClick(item, adapterPosition)
            }
        }


    }
}