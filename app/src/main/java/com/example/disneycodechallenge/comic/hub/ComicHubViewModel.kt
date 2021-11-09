package com.example.disneycodechallenge.comic.hub

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.disneycodechallenge.models.Comic
import com.example.disneycodechallenge.repositories.ComicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ComicHubViewModel @Inject constructor(comicRepository: ComicRepository) : ViewModel() {

    val comicList = comicRepository.comicFlow()
        .stateIn(
            initialValue = null,
            scope = viewModelScope,
            started = WhileSubscribed(5000)
        )
}