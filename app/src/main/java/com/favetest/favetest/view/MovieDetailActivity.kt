package com.favetest.favetest.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.favetest.favetest.R
import com.favetest.favetest.data.Genres
import com.favetest.favetest.data.StaticData
import com.favetest.favetest.data.StaticData.Companion.extraMovie
import com.favetest.favetest.databinding.MovieDetailActivityBinding
import com.favetest.favetest.model.Movie
import com.favetest.favetest.viewmodel.MovieDetailFetchViewModel
import com.favetest.favetest.viewmodel.MovieDetailViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.core.parameter.parametersOf
import java.util.*

@KoinApiExtension
class MovieDetailActivity : AppCompatActivity() {

    private fun addGenre(chipGroup: ChipGroup, genreName: ArrayList<Genres>?) {
        genreName?.forEach { index ->
            val chip = Chip(chipGroup.context)
            chip.text = index.name
            chipGroup.addView(chip)
        }

    }


    private var movieDetailActivityBinding: MovieDetailActivityBinding? = null
    private val movieDetailFetchViewModel by viewModel<MovieDetailFetchViewModel> ()
    private lateinit var movieID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDataBinding()
    }

    private fun initDataBinding() {
        getExtrasFromIntent()

//        movieDetailFetchViewModel = MovieDetailFetchViewModel(this)

        //        private val movieDetailFetchViewModel by viewModel<MovieDetailFetchViewModel> ()
//        val movieDetailViewModel  by viewModel<MovieDetailViewModel> ()

        movieDetailFetchViewModel.myLiveData.observe(this, androidx.lifecycle.Observer {

            when (it?.myDataType) {
                StaticData.firstTimeFetch -> {
                    val movieDetailViewModel  : MovieDetailViewModel by viewModel {
                        parametersOf(it.movies)
                    }

//                    val movieDetailViewModel  by viewModel<MovieDetailViewModel> (it.movies, this)
                    movieDetailActivityBinding = DataBindingUtil.setContentView(this, R.layout.movie_detail_activity)
                    movieDetailActivityBinding?.movieDetailViewModel = movieDetailViewModel
                    addGenre(movieDetailActivityBinding!!.chipGroup, movieDetailViewModel.getGenre())
                }
                StaticData.noInternet -> {
                    Toast.makeText(applicationContext, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
                }
                StaticData.genericError -> {
                    Toast.makeText(applicationContext, getString(R.string.something_wrong), Toast.LENGTH_SHORT).show()
                }
            }
        })

        movieDetailFetchViewModel.fetchMovieDetails(movieID)

    }

    private fun getExtrasFromIntent() {
        val movie = intent.getParcelableExtra<Movie>(extraMovie)
        movieID = movie!!.id
    }

    override fun onDestroy() {
        super.onDestroy()
        movieDetailFetchViewModel.reset()
    }

}