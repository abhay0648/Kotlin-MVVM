package com.favetest.favetest.data

import com.favetest.favetest.data.StaticData.Companion.baseURL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MovieFactory {

    fun create(): MovieService {
        val retrofit = Retrofit.Builder().baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit.create(MovieService::class.java)
    }


}
