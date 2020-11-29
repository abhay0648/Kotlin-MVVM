package com.favetest.favetest.model
import android.os.Parcelable
import com.favetest.favetest.data.Genres
import com.favetest.favetest.data.MovieResponseDetail
import com.favetest.favetest.data.SpokenLanguages
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie
    (var original_language: String, var original_title: String, var popularity: String, var release_date: String
     ,var title: String, var overview: String, var poster_path: String, var backdrop_path: String?
     , var id: String, var vote_average: String)
 : Parcelable {
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
                vote_average: String? = null,

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
                vote_average ?: "",
                )
    }
}


