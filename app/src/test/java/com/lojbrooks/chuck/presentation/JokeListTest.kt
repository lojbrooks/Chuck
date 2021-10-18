package com.lojbrooks.chuck.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lojbrooks.chuck.domain.common.RandomNumberGenerator
import com.lojbrooks.chuck.domain.usecase.GetJokesQuery
import com.lojbrooks.chuck.fakes.FakeJokeRepository
import com.lojbrooks.chuck.presentation.jokelist.JokeListViewModel
import com.lojbrooks.chuck.rules.CoroutineTestRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class JokeListTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    private lateinit var viewModel: JokeListViewModel
    private val jokeRepository = FakeJokeRepository()
    private val rng: RandomNumberGenerator = mock()

    private fun initViewModel() = runBlocking {
        viewModel = JokeListViewModel(GetJokesQuery(jokeRepository, rng))
    }

    @Test
    fun `WHEN init THEN show loading`() = runBlockingTest {
        coroutineRule.testDispatcher.pauseDispatcher()

        initViewModel()

        val state = viewModel.state.first()
        coroutineRule.testDispatcher.resumeDispatcher()
        assertThat(state, equalTo(JokeListViewModel.State.Loading))
    }

    @Test
    fun `WHEN init THEN show jokes`() = runBlockingTest {
        whenever(rng.getRandomInt(any())).thenReturn(2)

        initViewModel()

        val state = viewModel.state.value
        assertThat((state as JokeListViewModel.State.Data).jokes.size, equalTo(2))
    }

    @Test
    fun `GIVEN rng returns 10 WHEN init THEN show 10 jokes`() = runBlockingTest {
        whenever(rng.getRandomInt(any())).thenReturn(10)
        initViewModel()

        val state = viewModel.state.value
        assertThat((state as JokeListViewModel.State.Data).jokes.size, equalTo(10))
    }

    @Test
    fun `GIVEN repo throws DataFetchException WHEN init THEN show error`() = runBlockingTest {
        jokeRepository.throwDataFetchException = true

        initViewModel()

        val state = viewModel.state.value
        assertThat(state, equalTo(JokeListViewModel.State.Error))
    }

    @Test
    fun `WHEN try again clicked THEN show jokes`() = runBlockingTest {
        whenever(rng.getRandomInt(any())).thenReturn(5)
        jokeRepository.throwDataFetchException = true
        val states = mutableListOf<JokeListViewModel.State>()

        initViewModel()
        val job = launch { viewModel.state.take(3).toList(states) }
        jokeRepository.throwDataFetchException = false

        viewModel.onTryAgainClicked()

        assertThat(states[0], equalTo(JokeListViewModel.State.Error))
        assertThat(states[1], equalTo(JokeListViewModel.State.Loading))
        assertThat((states[2] as JokeListViewModel.State.Data).jokes.size, equalTo(5))

        job.cancel()
    }

    @Test
    fun `GIVEN repo throws DataFetchException WHEN try again clicked THEN show error`() = runBlockingTest {
        initViewModel()

        jokeRepository.throwDataFetchException = true
        viewModel.onTryAgainClicked()

        val state = viewModel.state.value
        assertThat(state, equalTo(JokeListViewModel.State.Error))
    }
}