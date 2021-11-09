package com.example.disneycodechallenge

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DisneyCodeChallengeViewModel : ViewModel() {
    private val _onErrorEvent = MutableLiveData<Throwable>()
    val onErrorEvent get() = _onErrorEvent
}