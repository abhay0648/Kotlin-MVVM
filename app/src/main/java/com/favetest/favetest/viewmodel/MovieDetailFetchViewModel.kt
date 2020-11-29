package com.favetest.favetest.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.favetest.favetest.MovieApplication
import com.favetest.favetest.data.*
import com.favetest.favetest.model.Movie
import com.favetest.favetest.model.MyViewState
import com.favetest.favetest.model.MyViewStateDetail
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.net.UnknownHostException
import java.util.*

@KoinApiExtension
class MovieDetailFetchViewModel(private val context: Context) : KoinComponent, ViewModel(){

    private lateinit var movieDetail: MovieResponseDetail
    private var compositeDisposable: CompositeDisposable? = CompositeDisposable()
    private var myViewState : MyViewStateDetail = MyViewStateDetail()
    val myLiveData = MutableLiveData<MyViewStateDetail>()

    internal fun fetchMovieDetails(movieID : String) {
        val movieService : MovieService by  inject()
        val scheduler : Scheduler by  inject()


        val disposable = movieService.fetchMovieDetail(movieID,StaticData.apiKey)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    myViewState.copy(isLoading = true)
                }
                .subscribe({ movieResponse ->
                    changeMovieDataSet(movieResponse)
                }, { error ->
                    myViewState = if (error is NoInternetException || error is UnknownHostException) {
                        myViewState.copy(myDataType = StaticData.noInternet,isLoading = false)
                    } else {
                        myViewState.copy(myDataType = StaticData.genericError,isLoading = false)
                    }

                    myLiveData.postValue(myViewState)
                })

        compositeDisposable?.add(disposable)
    }

    private fun changeMovieDataSet(movies: MovieResponseDetail) {
        movieDetail = movies

        myViewState = myViewState.copy(movies = movieDetail, myDataType = StaticData.firstTimeFetch,isLoading = false)
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