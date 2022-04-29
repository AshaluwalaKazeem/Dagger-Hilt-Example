package com.example.dagger_hilt_playground.repository

import com.example.dagger_hilt_playground.model.Blog
import com.example.dagger_hilt_playground.retrofit.BlogRetrofit
import com.example.dagger_hilt_playground.retrofit.NetworkMapper
import com.example.dagger_hilt_playground.room.BlogDao
import com.example.dagger_hilt_playground.room.CacheMapper
import com.example.dagger_hilt_playground.util.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepository
@Inject
constructor(
    private val blogRetrofit: BlogRetrofit,
    private val blogDao: BlogDao,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
)
{
    suspend fun getBlogs(): Flow<DataState<List<Blog>>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try{
            val networkBlogs = blogRetrofit.get()
            val blogs = networkMapper.mapFromEntityList(networkBlogs)
            for(blog in blogs){
                blogDao.insert(cacheMapper.mapToEntity(blog))
            }
            val cachedBlogs = blogDao.get()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedBlogs)))
        }catch (e: Exception){
            emit(DataState.Error(e))
        }
    }
}