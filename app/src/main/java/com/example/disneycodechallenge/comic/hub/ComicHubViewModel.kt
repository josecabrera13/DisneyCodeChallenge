package com.example.disneycodechallenge.comic.hub

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.disneycodechallenge.data.ComicRepository
import com.example.disneycodechallenge.models.Comic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ComicHubViewModel @Inject constructor(comicRepository: ComicRepository) : ViewModel() {
    val pagingDataFlow: Flow<PagingData<Comic>> =
        comicRepository.getComicListFlow().cachedIn(viewModelScope)
}

