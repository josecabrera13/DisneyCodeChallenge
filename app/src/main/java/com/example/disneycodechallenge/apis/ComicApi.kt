package com.example.disneycodechallenge.apis

import com.example.disneycodechallenge.apis.responses.ComicResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ComicApi {

    @GET("/v1/public/comics")
    suspend fun fetchComicList(
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 0
    ): Response<ComicResponse>

    @GET("/v1/public/comics/{comicId}")
    suspend fun fetchComic(@Path("comicId") comicId: Int): Response<ComicResponse>
}