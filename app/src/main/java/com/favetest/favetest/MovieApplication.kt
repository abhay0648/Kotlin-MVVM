package com.favetest.favetest

import android.app.Application
import android.content.Context
import com.favetest.favetest.data.MovieFactory
import com.favetest.favetest.data.MovieResponseDetail
import com.favetest.favetest.data.MovieService
import com.favetest.favetest.viewmodel.MovieDetailFetchViewModel
import com.favetest.favetest.viewmodel.MovieDetailViewModel
import com.favetest.favetest.viewmodel.MovieViewModel
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.core.context.startKoin
import org.koin.dsl.module

val appModule = module {
    single {
        MovieFactory().create()
    }

    single {
        Schedulers.io()
    }
}

@OptIn(KoinApiExtension::class)
val viewModule = module {
    viewModel {
        MovieViewModel(get())
    }

    viewModel {
        (data : MovieResponseDetail) -> MovieDetailViewModel(data, get())
    }

    viewModel {
        MovieDetailFetchViewModel(get())
    }

}

class MovieApplication : Application() {
    private var peopleService: MovieService? = null

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MovieApplication)
            modules(listOf(appModule,viewModule))
        }
    }

    private operator fun get(context: Context): MovieApplication {
        return context.applicationContext as MovieApplication
    }


    fun create(context: Context): MovieApplication {
        return get(context)
    }

    fun getPeopleService(): MovieService {
        if (peopleService == null) {
            peopleService = MovieFactory().create()
        }

        return peopleService as MovieService
    }


}