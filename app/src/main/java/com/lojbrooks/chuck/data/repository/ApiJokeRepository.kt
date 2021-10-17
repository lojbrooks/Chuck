package com.lojbrooks.chuck.data.repository

import com.lojbrooks.chuck.data.mapper.JokeMapper
import com.lojbrooks.chuck.data.remote.ChuckNorrisApi
import com.lojbrooks.chuck.domain.model.Joke
import com.lojbrooks.chuck.domain.repository.DataFetchException
import com.lojbrooks.chuck.domain.repository.JokeRepository
import java.io.IOException
import javax.inject.Inject

class ApiJokeRepository @Inject constructor(
    private val api: ChuckNorrisApi,
    private val jokeMapper: JokeMapper
) : JokeRepository {

    override suspend fun getJokes(amount: Int): List<Joke> {
        return try {
            val response = api.getJokes(amount)
            if (response.isSuccessful) {
                response.body()?.value?.map { jokeMapper.toDomain(it) }.orEmpty()
            } else throw DataFetchException()
        } catch (e: IOException) {
            throw DataFetchException()
        }
    }

}