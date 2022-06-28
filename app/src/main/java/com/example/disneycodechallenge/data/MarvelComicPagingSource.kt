package com.example.disneycodechallenge.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.disneycodechallenge.apis.ComicApi
import com.example.disneycodechallenge.data.ComicRepositoryImpl.Companion.NETWORK_PAGE_SIZE
import com.example.disneycodechallenge.models.Comic
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1
private const val TAG = "MarvelComicPagingSource"

class MarvelComicPagingSource(
    private val comicApi: ComicApi
) : PagingSource<Int, Comic>() {

    override fun getRefreshKey(state: PagingState<Int, Comic>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Comic> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val offset = (position - 1) * NETWORK_PAGE_SIZE
            Log.d(TAG, "offset : $offset")
            val response = comicApi.fetchComicList(
                offset = offset,
                limit = NETWORK_PAGE_SIZE
            )
            val results = response.body()?.data?.results ?: emptyList()
            val nextKey = if (results.isEmpty()) {
                null
            } else {
                position + 1
            }
            LoadResult.Page(
                data = results.map { it.toComic() },
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}