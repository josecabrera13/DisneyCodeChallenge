package com.example.disneycodechallenge.fakes

import com.example.disneycodechallenge.models.Comic
import com.example.disneycodechallenge.models.Failure
import com.example.disneycodechallenge.models.Result
import com.example.disneycodechallenge.models.Success
import com.example.disneycodechallenge.repositories.ComicRepository
import com.example.disneycodechallenge.test.data.TestData
import kotlinx.coroutines.flow.flow
import java.io.IOException

class FakeComicRepository : ComicRepository {
    override fun comicFlow(comicId: Int?) = flow<Result<List<Comic>, Throwable>> {
        val comic = TestData.comicList.find { it.id == comicId }
        if (comic == null) {
            emit(Failure(IOException()))
        } else {
            emit(Success(listOf(comic)))
        }

    }
}