package com.lojbrooks.chuck.di

import com.lojbrooks.chuck.data.remote.ChuckNorrisApi
import com.lojbrooks.chuck.fakes.FakeChuckNorrisApi
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
class FakeNetworkModule {

    @Provides
    @Singleton
    fun provideChuckNorrisApi(fakeChuckNorrisApi: FakeChuckNorrisApi): ChuckNorrisApi {
        return fakeChuckNorrisApi
    }
}
