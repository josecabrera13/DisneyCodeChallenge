package com.example.disneycodechallenge.comic.hub

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.disneycodechallenge.DisneyCodeChallengeViewModel
import com.example.disneycodechallenge.databinding.FragmentComicHubBinding
import com.example.disneycodechallenge.models.Comic
import com.example.disneycodechallenge.models.Failure
import com.example.disneycodechallenge.models.Success
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ComicHubFragment : Fragment() {

    private lateinit var binding: FragmentComicHubBinding
    private val viewModel: ComicHubViewModel by viewModels()
    private val appViewModel: DisneyCodeChallengeViewModel by activityViewModels()
    private lateinit var comicAdapter: ComicAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentComicHubBinding.inflate(inflater)
        comicAdapter = ComicAdapter(this::onComicSelected)
        binding.comicList.apply {
            adapter = comicAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            viewModel.comicList.flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
                .collect { result ->
                    result?.let {
                        if (it is Success) {
                            comicAdapter.submitList(it.value)
                        } else {
                            appViewModel.onErrorEvent.postValue((it as Failure).error)
                        }
                    }
                }
        }
    }

    private fun onComicSelected(comic: Comic?) {
        comic?.let {
            val action = ComicHubFragmentDirections.comicHubFragmentToComicDetailFragment(comic.id)
            findNavController().navigate(action)
        }
    }
}