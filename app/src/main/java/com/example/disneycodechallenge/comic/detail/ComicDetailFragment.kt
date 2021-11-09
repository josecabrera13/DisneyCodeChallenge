package com.example.disneycodechallenge.comic.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.disneycodechallenge.DisneyCodeChallengeViewModel
import com.example.disneycodechallenge.databinding.FragmentComicDetailBinding
import com.example.disneycodechallenge.models.Comic
import com.example.disneycodechallenge.models.Failure
import com.example.disneycodechallenge.models.Success
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ComicDetailFragment : Fragment() {

    private lateinit var binding: FragmentComicDetailBinding
    private val appViewModel: DisneyCodeChallengeViewModel by activityViewModels()
    private val viewModel: ComicDetailViewModel by viewModels()
    private val arguments: ComicDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentComicDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            viewModel.comicFlowById(arguments.comicId)
                .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
                .collect { result ->
                    result?.let {
                        if (it is Success) {
                            presentComicDetail(it.value)
                        } else {
                            appViewModel.onErrorEvent.postValue((it as Failure).error)
                        }
                    }
                }
        }
    }

    private fun presentComicDetail(comic: Comic) {
        Glide.with(this).load(comic.imageUrl).into(binding.comicThumbnail)
        binding.comicTitle.text = comic.title
        binding.comicDescription.text = comic.description
    }
}