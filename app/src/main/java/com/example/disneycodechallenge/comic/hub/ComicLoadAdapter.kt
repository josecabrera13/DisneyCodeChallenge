package com.example.disneycodechallenge.comic.hub

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.disneycodechallenge.R
import com.example.disneycodechallenge.databinding.ComicLoadStateFooterViewItemBinding

class ComicLoadAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<ComicLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: ComicLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ComicLoadStateViewHolder {
        return ComicLoadStateViewHolder.create(parent, retry)
    }
}

class ComicLoadStateViewHolder(
    private val binding: ComicLoadStateFooterViewItemBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState is LoadState.Error
        binding.errorMsg.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): ComicLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.comic_load_state_footer_view_item, parent, false)
            val binding = ComicLoadStateFooterViewItemBinding.bind(view)
            return ComicLoadStateViewHolder(binding, retry)
        }
    }
}
