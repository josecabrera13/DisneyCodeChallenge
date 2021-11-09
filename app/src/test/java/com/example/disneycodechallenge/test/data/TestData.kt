package com.example.disneycodechallenge.test.data

import com.example.disneycodechallenge.models.Comic

class TestData {
    companion object {
        val comicList = listOf(
            Comic(1, "title1", "description1", "imageUrl1"),
            Comic(2, "title2", "description2", "imageUrl2")
        )
    }
}
