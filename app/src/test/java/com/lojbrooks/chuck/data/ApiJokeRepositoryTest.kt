package com.lojbrooks.chuck.data

import com.lojbrooks.chuck.data.mapper.JokeMapper
import com.lojbrooks.chuck.data.remote.ChuckNorrisApi
import com.lojbrooks.chuck.data.remote.JokeDto
import com.lojbrooks.chuck.data.remote.JokesResponseDto
import com.lojbrooks.chuck.data.repository.ApiJokeRepository
import com.lojbrooks.chuck.domain.model.Joke
import com.lojbrooks.chuck.domain.repository.DataFetchException
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response
import java.io.IOException

class ApiJokeRepositoryTest {

    private lateinit var repo: ApiJokeRepository

    private val api: ChuckNorrisApi = mock()

    @Before
    fun setup() {
        repo = ApiJokeRepository(api, JokeMapper())
    }

    @Test
    fun `GIVEN api returns jokes WHEN getJokes THEN return jokes`() = runBlockingTest {
        whenever(api.getJokes(any())).thenReturn(Response.success(JokesResponseDto("success", listOf(
            JokeDto(1, "Knock knock"),
            JokeDto(2, "Who's there?")
        ))))

        val result = repo.getJokes(2)

        assertThat(result, equalTo(listOf(
            Joke(1, "Knock knock"),
            Joke(2, "Who's there?")
        )))
    }

    @Test(expected = DataFetchException::class)
    fun `GIVEN api response unsuccessful WHEN getJokes THEN throw`() = runBlockingTest {
        whenever(api.getJokes(any())).thenReturn(Response.error(500, "".toResponseBody()))

        repo.getJokes(2)
    }

    @Test(expected = DataFetchException::class)
    fun `GIVEN api throws IOException WHEN getJokes THEN throw`() = runBlockingTest {
        whenever(api.getJokes(any())).thenAnswer { throw IOException() }

        repo.getJokes(2)
    }
}