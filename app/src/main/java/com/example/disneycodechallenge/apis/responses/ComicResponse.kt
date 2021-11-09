package com.example.disneycodechallenge.apis.responses

import com.example.disneycodechallenge.models.Comic

data class ComicResponse(
    val code: Int,
    val status: String,
    val data: Data
)

data class Thumbnail(
    val path: String,
    val extension: String
)

data class Result(
    val id: Int,
    val title: String,
    val thumbnail: Thumbnail,
    val description: String?
) {
    fun toComic(): Comic {
        val imageUrl = thumbnail.path + "." + thumbnail.extension
        return Comic(id, title, description, imageUrl)
    }
}

data class Data(
    val results: List<Result>
)