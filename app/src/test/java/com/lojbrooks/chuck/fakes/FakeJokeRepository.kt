package com.lojbrooks.chuck.fakes

import com.lojbrooks.chuck.domain.model.Joke
import com.lojbrooks.chuck.domain.repository.DataFetchException
import com.lojbrooks.chuck.domain.repository.JokeRepository

class FakeJokeRepository : JokeRepository {

    var throwDataFetchException = false

    override suspend fun getJokes(amount: Int): List<Joke> {
        if (throwDataFetchException) throw DataFetchException()

        val jokes = mutableListOf<Joke>()
        for (i in 0 until amount) {
            jokes.add(Joke(id = i, text = "Joke $i"))
        }
        return jokes
    }
}