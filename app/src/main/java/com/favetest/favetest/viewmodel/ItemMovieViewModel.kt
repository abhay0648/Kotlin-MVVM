package com.favetest.favetest.viewmodel

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.favetest.favetest.data.StaticData.Companion.imageBaseURL
import com.favetest.favetest.data.StaticData.Companion.launchDetail
import com.favetest.favetest.model.Movie


@BindingAdapter("posterPathURL")
fun setPosterPathURL(imageView: ImageView, url: String) {
    Glide.with(imageView.context).load(url).apply(
        RequestOptions()
            .centerCrop()).into(imageView)
}

class ItemMovieViewModel(private var movie: Movie, private val context: Context) : BaseObservable() {

    fun getOriginalTitle(): String {
        return movie.original_title
    }

    fun getPopularity(): String {
        return movie.popularity
    }

    fun getPosterPathURL(): String {
        return imageBaseURL+ movie.poster_path
    }

    fun getOverview(): String {
        return movie.overview
    }

    fun getVoteAverage(): String {
        return movie.vote_average
    }


    fun onItemClick(view: View) {
        context.startActivity(launchDetail(view.context, movie))
    }

    fun setMovie(movie: Movie) {
        this.movie = movie
        notifyChange()
    }
}