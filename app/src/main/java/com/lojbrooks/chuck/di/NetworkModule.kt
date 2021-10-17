package com.lojbrooks.chuck.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.lojbrooks.chuck.data.remote.ChuckNorrisApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.icndb.com")
            .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideChuckNorrisApi(retrofit: Retrofit): ChuckNorrisApi {
        return retrofit.create(ChuckNorrisApi::class.java)
    }

}
