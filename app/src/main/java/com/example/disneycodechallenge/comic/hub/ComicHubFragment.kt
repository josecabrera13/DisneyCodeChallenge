package com.example.disneycodechallenge.comic.hub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.disneycodechallenge.DisneyCodeChallengeViewModel
import com.example.disneycodechallenge.databinding.FragmentComicHubBinding
import com.example.disneycodechallenge.models.Comic
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ComicHubFragment : Fragment() {

    private lateinit var binding: FragmentComicHubBinding
    private val viewModel: ComicHubViewModel by viewModels()
    private val appViewModel: DisneyCodeChallengeViewModel by activityViewModels()
    private val comicAdapter: ComicAdapter = ComicAdapter(this::onComicSelected)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentComicHubBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initComicList()
    }

    private fun initComicList() {
        binding.swipeRefresh.setOnRefreshListener {
            comicAdapter.retry()
        }
        binding.comicList.apply {
            adapter = comicAdapter.withLoadStateHeaderAndFooter(
                header = ComicLoadAdapter { comicAdapter.retry() },
                footer = ComicLoadAdapter { comicAdapter.retry() }
            )
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }

        lifecycleScope.launch {
            viewModel.pagingDataFlow.collectLatest(comicAdapter::submitData)
        }
        lifecycleScope.launch {
            comicAdapter.loadStateFlow.collect { loadState ->
                binding.swipeRefresh.isRefreshing = loadState.source.refresh is LoadState.Loading
                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                    ?: loadState.refresh as? LoadState.Error
                errorState?.let {
                    if (binding.comicList.adapter?.itemCount == 0) {
                        appViewModel.onErrorEvent.postValue(it.error)
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