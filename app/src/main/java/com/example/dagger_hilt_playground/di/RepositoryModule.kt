package com.example.dagger_hilt_playground.di

import com.example.dagger_hilt_playground.repository.MainRepository
import com.example.dagger_hilt_playground.retrofit.BlogRetrofit
import com.example.dagger_hilt_playground.retrofit.NetworkMapper
import com.example.dagger_hilt_playground.room.BlogDao
import com.example.dagger_hilt_playground.room.CacheMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        blogDao: BlogDao,
        retrofit: BlogRetrofit,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ): MainRepository{
        return MainRepository(blogRetrofit = retrofit, blogDao, cacheMapper, networkMapper)
    }
}