package com.favetest.favetest.data

import android.os.Parcelable
import com.favetest.favetest.model.Movie
import kotlinx.android.parcel.Parcelize


data class MovieResponse(var results: ArrayList<Movie>)


data class MovieResponseDetail(var original_language: String, var original_title: String, var popularity: String, var release_date: String
                               ,var title: String, var overview: String, var poster_path: String, var backdrop_path: String
                               , var id: String, var status: String, var runtime: Int, var spoken_languages: ArrayList<SpokenLanguages>?
                               , var genres: ArrayList<Genres>?) {
    companion object {
        operator fun invoke(
                original_language: String? = null,
                original_title: String? = null,
                popularity: String? = null,
                release_date: String? = null,
                title: String? = null,
                overview: String? = null,
                poster_path: String? = null,
                backdrop_path: String? = null,
                id: String? = null,
                status: String? = null,
                runtime: Int? = null,
                spoken_languages: ArrayList<SpokenLanguages>? = null,
                genres: ArrayList<Genres>? = null,


        ) = MovieResponseDetail(
                original_language ?: "",
                original_title ?: "",
                popularity ?: "",
                release_date ?: "",
                title ?: "",
                overview ?: "",
                poster_path ?: "",
                backdrop_path ?: "",
                id ?: "",
                status ?: "",
                runtime ?: 0,
                spoken_languages ?: ArrayList() ,
                genres ?: ArrayList()
        )
    }
}

@Parcelize
data class SpokenLanguages(var english_name: String, var name: String) : Parcelable

@Parcelize
data class Genres(var id: String, var name: String) : Parcelable