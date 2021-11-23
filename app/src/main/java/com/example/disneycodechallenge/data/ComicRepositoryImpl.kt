package com.example.disneycodechallenge.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.disneycodechallenge.apis.ComicApi
import com.example.disneycodechallenge.models.Comic
import com.example.disneycodechallenge.models.Failure
import com.example.disneycodechallenge.models.Result
import com.example.disneycodechallenge.models.Success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ComicRepositoryImpl @Inject constructor(
    private val comicApi: ComicApi,
    private val coroutineDispatcher: CoroutineDispatcher
) : ComicRepository {

    override fun comicFlow(comicId: Int) = flow<Result<List<Comic>, Throwable>> {
        val response = comicApi.fetchComic(comicId)
        val comicResponse = response.body()
        if (response.isSuccessful && comicResponse != null) {
            emit(Success(comicResponse.data.results.map { it.toComic() }))
        }
    }.catch { error ->
        emit(Failure(error))
    }.flowOn(coroutineDispatcher).conflate()

    override fun getComicListFlow(): Flow<PagingData<Comic>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MarvelComicPagingSource(comicApi) }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 100
    }
}

interface ComicRepository {
    fun comicFlow(comicId: Int): Flow<Result<List<Comic>, Throwable>>
    fun getComicListFlow(): Flow<PagingData<Comic>>
}