package com.example.dagger_hilt_playground.retrofit

import retrofit2.http.GET

interface BlogRetrofit {

    @GET("blogs")
    suspend fun get() : List<BlogNetworkEntity>
}