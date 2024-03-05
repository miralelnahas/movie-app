package com.trianglz.ui.views.movies

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.trianglz.data.models.movies.Movie
import com.trianglz.ui.R
import com.trianglz.ui.base.BaseViewHolder
import com.trianglz.ui.viewmodels.movies.MoviesViewModel

class MoviesAdapter(private val vm: MoviesViewModel) :
    PagingDataAdapter<Movie, BaseViewHolder>(MovieDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        BaseViewHolder.from(parent, R.layout.item_movie)

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(vm.getMovieUiModel(it)) }
    }
}