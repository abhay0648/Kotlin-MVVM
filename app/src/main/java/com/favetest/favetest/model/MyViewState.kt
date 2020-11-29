package com.favetest.favetest.model

import com.favetest.favetest.data.MovieResponseDetail

data class MyViewState(val movies: ArrayList<Movie> = ArrayList(), val myDataType: String = "",
                       val isLoading: Boolean = false)

data class MyViewStateDetail(val movies: MovieResponseDetail = MovieResponseDetail(), val myDataType: String = "",
                       val isLoading: Boolean = false)