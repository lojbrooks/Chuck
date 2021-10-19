package com.lojbrooks.chuck.fakes

import com.lojbrooks.chuck.data.remote.ChuckNorrisApi
import com.lojbrooks.chuck.data.remote.JokeDto
import com.lojbrooks.chuck.data.remote.JokesResponseDto
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeChuckNorrisApi @Inject constructor() : ChuckNorrisApi {

    var getJokesResponse: Response<JokesResponseDto> = Response.success(JokesResponseDto(
        type = "success",
        value = listOf(
            JokeDto(1, "Once death had a near Chuck Norris experience."),
            JokeDto(2, "Love does not hurt. Chuck Norris does."),
            JokeDto(3, "Movie trivia: The movie &quot;Invasion U.S.A&quot; is, in fact, a documentary.")
        )
    ))

    override suspend fun getJokes(amount: Int): Response<JokesResponseDto> {
        return getJokesResponse
    }
}