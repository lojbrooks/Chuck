package com.lojbrooks.chuck.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ChuckNorrisApi {

    @GET("/jokes/random/{amount}")
    suspend fun getJokes(@Path("amount") amount: Int) : Response<JokesResponseDto>
}