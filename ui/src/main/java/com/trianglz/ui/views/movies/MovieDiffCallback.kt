package com.trianglz.ui.views.movies

import androidx.recyclerview.widget.DiffUtil
import com.trianglz.data.models.movies.Movie

object MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
}