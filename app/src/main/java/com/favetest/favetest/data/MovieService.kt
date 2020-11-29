package com.favetest.favetest.data

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url


interface MovieService {
    @GET("3/movie/popular")
    fun fetchMovie(@Query("api_key") apiKey: String,
                   @Query("sort_by") string: String,
                   @Query("page") pageNo: String): Observable<MovieResponse>

    @GET("3/movie/{movieID}")
    fun fetchMovieDetail(@Path("movieID") movieID : String,
                         @Query("api_key") apiKey: String): Observable<MovieResponseDetail>

}