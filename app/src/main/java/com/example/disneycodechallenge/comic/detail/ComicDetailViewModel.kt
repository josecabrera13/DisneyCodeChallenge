package com.example.disneycodechallenge.comic.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.disneycodechallenge.models.Failure
import com.example.disneycodechallenge.models.Success
import com.example.disneycodechallenge.data.ComicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ComicDetailViewModel @Inject constructor(
    private val comicRepository: ComicRepository
) : ViewModel() {

    fun comicFlowById(comicId: Int) = comicRepository.comicFlow(comicId).map { result ->
        if (result is Success) {
            Success(result.value.first())
        } else result as Failure
    }.stateIn(
        initialValue = null,
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000)
    )
}