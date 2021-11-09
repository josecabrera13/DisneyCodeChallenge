package com.example.disneycodechallenge.comic.hub

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.disneycodechallenge.databinding.ComicItemBinding
import com.example.disneycodechallenge.models.Comic

class ComicAdapter(
    private val onComicSelected: (comic: Comic?) -> Unit
) : ListAdapter<Comic, ComicViewHolder>(ComicDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder =
        ComicViewHolder(
            onComicSelected,
            ComicItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        holder.bindComic(currentList[position])
    }

    private class ComicDiff : DiffUtil.ItemCallback<Comic>() {
        override fun areItemsTheSame(oldItem: Comic, newItem: Comic): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Comic, newItem: Comic): Boolean =
            oldItem == newItem
    }
}

class ComicViewHolder(
    val onComicSelected: (comic: Comic?) -> Unit,
    private val binding: ComicItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.setOnClickListener { onComicSelected(itemView.tag as? Comic) }
    }

    fun bindComic(comic: Comic) {
        binding.root.tag = comic
        binding.comicTitle.text = comic.title
        Glide.with(this.itemView).load(comic.imageUrl).into(binding.comicThumbnail)
    }
}