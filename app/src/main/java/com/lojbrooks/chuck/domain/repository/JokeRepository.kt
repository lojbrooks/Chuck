package com.lojbrooks.chuck.domain.repository

import com.lojbrooks.chuck.domain.model.Joke

interface JokeRepository {
    suspend fun getJokes(amount: Int) : List<Joke>
}