package com.favetest.favetest.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.favetest.favetest.R
import com.favetest.favetest.data.StaticData
import com.favetest.favetest.databinding.MovieActivityBinding
import com.favetest.favetest.viewmodel.MovieViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class MovieActivity : AppCompatActivity() {

    private lateinit var movieActivityBinding: MovieActivityBinding
    private lateinit var movieAdapter : MovieAdapter
    private var visibleThreshold = 5
    private var isLoading = false
    private var pagination = 1
    val movieViewModelApp by viewModel<MovieViewModel> ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
    }

    private fun initDataBinding() {
        movieActivityBinding = DataBindingUtil.setContentView(this, R.layout.movie_activity)
        movieActivityBinding.movieViewModel = movieViewModelApp

        setupListMovieView(movieActivityBinding.listMovie)

        movieViewModelApp.myLiveData.observe(this, androidx.lifecycle.Observer {

            isLoading = it.isLoading
            when (it?.myDataType) {
                StaticData.firstTimeFetch -> {
                    movieAdapter.setMovieList(it.movies)
                }
                StaticData.noInternet -> {
                    Toast.makeText(applicationContext,getString(R.string.no_internet),Toast.LENGTH_SHORT).show()
                }
                StaticData.genericError -> {
                    Toast.makeText(applicationContext,getString(R.string.something_wrong),Toast.LENGTH_SHORT).show()
                }
            }
        })

        movieViewModelApp.fetchMovieList(pagination.toString(), false)
    }

    private fun setupListMovieView(listMovie: RecyclerView) {
        val adapter = MovieAdapter(this, movieList = emptyList())
        val linearLayoutManager = LinearLayoutManager(this)
        listMovie.adapter = adapter
        listMovie.layoutManager = linearLayoutManager
        movieActivityBinding.swipeRefresh.isRefreshing = true

        movieAdapter = movieActivityBinding.listMovie.adapter as MovieAdapter

        setUpLoadMoreListener(listMovie, linearLayoutManager)

        movieActivityBinding.swipeRefresh.setOnRefreshListener {
            pagination = 1
            movieViewModelApp.fetchMovieList(pagination.toString(), false)
        }
    }

      private fun setUpLoadMoreListener(listMovie: RecyclerView, linearLayoutManager: LinearLayoutManager) {
         listMovie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
             override fun onScrolled(
                 recyclerView: RecyclerView,
                 dx: Int, dy: Int
             ) {
                 super.onScrolled(recyclerView, dx, dy)
                 val totalItemCount = linearLayoutManager.itemCount
                 val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                 if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold
                 ) {
                     isLoading = true
                     pagination += 1
                     movieViewModelApp.fetchMovieList(pagination.toString(), true)
                 }
             }
         })
    }


    override fun onDestroy() {
        super.onDestroy()
        movieViewModelApp.reset()
    }
}