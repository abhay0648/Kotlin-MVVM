package com.favetest.favetest.data

import android.content.Context
import android.content.Intent
import com.favetest.favetest.model.Movie
import com.favetest.favetest.view.MovieDetailActivity
import com.favetest.favetest.view.WebViewActivity


class StaticData {
    companion object {
        const val baseURL = "http://api.themoviedb.org/"

        const val backDropUrl = "backDropUrl"

        const val notSpecified = "Not Specified"
        const val imageBaseURL = "https://image.tmdb.org/t/p/w500/"

        const val webURL = "https://www.cathaycineplexes.com.sg/"
        const val extraMovie = "extraMovie"

        const val firstTimeFetch = "firstTimeFetch"
        const val noInternet = "noInternet"
        const val genericError = "genericError"

        const val apiKey = "328c283cd27bd1877d9080ccb1604c91"
        const val orderBy = "release_date.desc"

        fun launchDetail(context: Context, movie: Movie): Intent {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(extraMovie, movie)
            return intent
        }

        fun launchWebView(context: Context): Intent {
            return Intent(context, WebViewActivity::class.java)
        }
    }

}