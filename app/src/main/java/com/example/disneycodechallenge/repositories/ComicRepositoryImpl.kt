package com.example.disneycodechallenge.repositories

import com.example.disneycodechallenge.apis.ComicApi
import com.example.disneycodechallenge.models.Comic
import com.example.disneycodechallenge.models.Failure
import com.example.disneycodechallenge.models.Result
import com.example.disneycodechallenge.models.Success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

//TODO we can implement pagination to get the comic lis
// https://developer.android.com/topic/libraries/architecture/paging/v3-migration
class ComicRepositoryImpl @Inject constructor(
    private val comicApi: ComicApi,
    private val coroutineDispatcher: CoroutineDispatcher
) : ComicRepository {

    override fun comicFlow(comicId: Int?) = flow<Result<List<Comic>, Throwable>> {
        val response = comicId?.let { comicApi.fetchComic(it) } ?: comicApi.fetchComicList()
        val comicResponse = response.body()
        if (response.isSuccessful && comicResponse != null) {
            emit(Success(comicResponse.data.results.map { it.toComic() }))
        }
    }.catch { error ->
        emit(Failure(error))
    }.flowOn(coroutineDispatcher).conflate()
}

interface ComicRepository {
    fun comicFlow(comicId: Int? = null): Flow<Result<List<Comic>, Throwable>>
}