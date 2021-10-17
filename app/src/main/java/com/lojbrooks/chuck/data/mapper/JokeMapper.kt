package com.lojbrooks.chuck.data.mapper

import com.lojbrooks.chuck.data.remote.JokeDto
import com.lojbrooks.chuck.domain.model.Joke
import javax.inject.Inject

class JokeMapper @Inject constructor() {
    fun toDomain(dto: JokeDto): Joke {
        return Joke(
            id = dto.id,
            text = dto.joke
        )
    }
}