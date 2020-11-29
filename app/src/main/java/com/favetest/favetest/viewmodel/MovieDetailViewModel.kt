package com.favetest.favetest.viewmodel

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.favetest.favetest.data.Genres
import com.favetest.favetest.data.MovieResponseDetail
import com.favetest.favetest.data.StaticData.Companion.backDropUrl
import com.favetest.favetest.data.StaticData.Companion.imageBaseURL
import com.favetest.favetest.data.StaticData.Companion.launchWebView
import com.favetest.favetest.data.StaticData.Companion.notSpecified

@BindingAdapter(backDropUrl)
fun setBackDropUrl(imageView: ImageView, url: String) {
        Glide.with(imageView.context).load(url).apply(
                RequestOptions()
                        .centerCrop()).into(imageView)
}

class MovieDetailViewModel(private val movie: MovieResponseDetail, private val context: Context)  : ViewModel() {

    fun getOriginalLanguage(): String {
        return movie.original_language
    }

    fun getOriginalTitle(): String {
        return movie.original_title
    }

    fun getPopularity(): String {
        return movie.popularity
    }

    fun getReleaseDate(): String {
        return movie.release_date
    }

    fun getRuntime(): Int {
        return movie.runtime
    }

    fun getLocal(): String? {
        return if(movie.spoken_languages.isNullOrEmpty())
            notSpecified
        else
            movie.spoken_languages?.get(0)?.name
    }

    fun getStatus(): String {
        return movie.status
    }

    fun getTitle(): String {
        return movie.title
    }

    fun getOverview(): String {
        return movie.overview
    }

    fun getPosterPath(): String {
        return movie.poster_path
    }


    fun getBackDropPicture(): String {
        return imageBaseURL+ movie.backdrop_path
    }

    fun getGenre(): ArrayList<Genres>? {
        return movie.genres
    }

    fun onItemClick(view: View) {
        context.startActivity(launchWebView(view.context))
    }

}