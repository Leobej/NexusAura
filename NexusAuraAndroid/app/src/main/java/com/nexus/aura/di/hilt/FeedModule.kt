package com.nexus.aura.di.hilt

import com.nexus.aura.domain.repository.FakeFeedRepository
import com.nexus.aura.domain.repository.FeedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeedModule {

    @Provides
    @Singleton
    fun provideFeedRepository(): FeedRepository = FakeFeedRepository()
}