package com.lojbrooks.chuck.domain.usecase

import com.lojbrooks.chuck.domain.common.RandomNumberGenerator
import com.lojbrooks.chuck.domain.model.Joke
import com.lojbrooks.chuck.domain.repository.DataFetchException
import com.lojbrooks.chuck.domain.repository.JokeRepository
import javax.inject.Inject

class GetJokesQuery @Inject constructor(
    private val jokeRepository: JokeRepository,
    private val rng: RandomNumberGenerator
    ) {

    sealed class Result {
        data class Success(val jokes: List<Joke>): Result()
        object Error : Result()
    }

    suspend operator fun invoke(): Result {
        return try {
            Result.Success(jokeRepository.getJokes(rng.getRandomInt(MIN_JOKES..MAX_JOKES)))
        } catch (e: DataFetchException) {
            Result.Error
        }
    }

    companion object {
        private const val MIN_JOKES = 8
        private const val MAX_JOKES = 21
    }
}