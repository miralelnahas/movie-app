package com.trianglz.ui.views.movieDetails

import android.graphics.Color
import androidx.fragment.app.viewModels
import com.trianglz.ui.R
import com.trianglz.ui.base.BaseFragment
import com.trianglz.ui.databinding.FragmentMovieDetailsBinding
import com.trianglz.ui.viewmodels.movieDetails.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment :
    BaseFragment<FragmentMovieDetailsBinding>(R.layout.fragment_movie_details) {
    override val vm: MovieDetailsViewModel by viewModels()

    override fun initViews() {
        super.initViews()
        vb.toolbar.setNavigationOnClickListener {
            vm.onBackClick()
        }
    }
}