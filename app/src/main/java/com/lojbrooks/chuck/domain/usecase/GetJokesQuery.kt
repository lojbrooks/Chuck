package com.lojbrooks.chuck.domain.usecase

import com.lojbrooks.chuck.domain.model.Joke
import com.lojbrooks.chuck.domain.repository.DataFetchException
import com.lojbrooks.chuck.domain.repository.JokeRepository
import javax.inject.Inject

class GetJokesQuery @Inject constructor(private val jokeRepository: JokeRepository) {

    sealed class Result {
        data class Success(val jokes: List<Joke>): Result()
        object Error : Result()
    }

    suspend operator fun invoke(): Result {
        return try {
            Result.Success(jokeRepository.getJokes(5))
        } catch (e: DataFetchException) {
            Result.Error
        }
    }
}