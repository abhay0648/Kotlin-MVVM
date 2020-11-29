package com.favetest.favetest.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.favetest.favetest.R
import com.favetest.favetest.databinding.ItemMovieBinding
import com.favetest.favetest.model.Movie
import com.favetest.favetest.viewmodel.ItemMovieViewModel

class MovieAdapter(private val context: Context, private var movieList: List<Movie> = emptyList()) : RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapterViewHolder {
        val itemMovieBinding: ItemMovieBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.item_movie,
            parent,
            false)
        return MovieAdapterViewHolder(itemMovieBinding)
    }

    override fun onBindViewHolder(holder: MovieAdapterViewHolder, position: Int) {
        holder.bindMovie(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun setMovieList(movieList: List<Movie>?) {
        this.movieList = movieList!!
        notifyDataSetChanged()
    }

    class MovieAdapterViewHolder constructor(private var mItemMovieBinding: ItemMovieBinding) : RecyclerView.ViewHolder(mItemMovieBinding.itemMovie) {

        fun bindMovie(movie: Movie) {
            if (mItemMovieBinding.movieViewModel == null) {
                mItemMovieBinding.movieViewModel = ItemMovieViewModel(movie, itemView.context)
            } else {
                Log.e("mItemMovieBinding","mItemMovieBinding")
                mItemMovieBinding.movieViewModel?.setMovie(movie)
            }
        }
    }
}