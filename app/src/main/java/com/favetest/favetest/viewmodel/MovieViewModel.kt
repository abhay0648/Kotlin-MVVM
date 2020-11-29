package com.favetest.favetest.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.favetest.favetest.R
import com.favetest.favetest.data.MovieService
import com.favetest.favetest.data.NoInternetException
import com.favetest.favetest.data.StaticData
import com.favetest.favetest.data.StaticData.Companion.firstTimeFetch
import com.favetest.favetest.model.Movie
import com.favetest.favetest.model.MyViewState
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.net.UnknownHostException
import kotlin.collections.ArrayList


@KoinApiExtension
class MovieViewModel(context: Context) : KoinComponent, ViewModel() {

    var isLoading: ObservableBoolean = ObservableBoolean(false)
    private var movieList: ArrayList<Movie> = ArrayList()
    private var compositeDisposable: CompositeDisposable? = CompositeDisposable()
    private var myViewState : MyViewState = MyViewState()
    val myLiveData = MutableLiveData<MyViewState>()

    internal fun fetchMovieList(currentPage:String, isLoadMore :Boolean) {
        val movieService : MovieService by  inject()
        val scheduler : Scheduler by  inject()

        val disposable = movieService.fetchMovie(StaticData.apiKey,StaticData.orderBy,currentPage)
            .subscribeOn(scheduler)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                isLoading.set(true)
                myViewState.copy(isLoading = true)
            }
            .subscribe({ movieResponse ->
                isLoading.set(false)

                changeMovieDataSet(movieResponse.results, isLoadMore)
            }, { error ->
                myViewState = if (error is NoInternetException || error is UnknownHostException) {
                    myViewState.copy(myDataType = StaticData.noInternet,isLoading = false)
                } else {
                    myViewState.copy(myDataType = StaticData.genericError,isLoading = false)
                }

                isLoading.set(false)

                myLiveData.postValue(myViewState)

            })

        compositeDisposable?.add(disposable)
    }

    private fun changeMovieDataSet(movies: ArrayList<Movie>, isLoadMore: Boolean) {
        if (isLoadMore)
            movieList.addAll(movies)
        else
            movieList = movies

        movieList.sortByDescending{it.release_date}

        myViewState = myViewState.copy(movies = movieList, myDataType = firstTimeFetch,isLoading = false)
        myLiveData.postValue(myViewState)
    }

    private fun unSubscribeFromObservable() {
        if (compositeDisposable != null && !compositeDisposable!!.isDisposed) {
            compositeDisposable!!.dispose()
        }
    }

    fun reset() {
        unSubscribeFromObservable()
        compositeDisposable = null
    }
}