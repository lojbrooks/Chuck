package com.lojbrooks.chuck.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class JokesResponseDto(
    val type: String,
    val value: List<JokeDto>
)

@Serializable
data class JokeDto(
    val id: Int,
    val joke: String
)
