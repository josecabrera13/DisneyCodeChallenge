package com.example.disneycodechallenge.comic.detail

import com.example.disneycodechallenge.MainCoroutineRule
import com.example.disneycodechallenge.fakes.FakeComicRepository
import com.example.disneycodechallenge.models.Failure
import com.example.disneycodechallenge.models.Success
import com.example.disneycodechallenge.test.data.TestData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ComicDetailViewModelTest {

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @Test
    fun testComicFlowByIdSuccess() = coroutineRule.runBlockingTest {
        val viewModel = ComicDetailViewModel(FakeComicRepository())
        val expectedComic = TestData.comicList.random()
        val result = viewModel.comicFlowById(expectedComic.id).first()
        assertThat((result as Success).value, equalTo(expectedComic))
    }

    @Test
    fun testComicFlowByIdFailure() = coroutineRule.runBlockingTest {
        val viewModel = ComicDetailViewModel(FakeComicRepository())
        val result = viewModel.comicFlowById(Int.MAX_VALUE).first()
        assertThat((result as Failure).error, instanceOf(Throwable::class.java))
    }
}