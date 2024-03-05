package com.trianglz.ui.views.movies

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.trianglz.data.models.movies.SortType
import com.trianglz.ui.R
import com.trianglz.ui.base.BaseFragment
import com.trianglz.ui.databinding.FragmentMoviesBinding
import com.trianglz.ui.utils.Extensions.observe
import com.trianglz.ui.viewmodels.movies.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : BaseFragment<FragmentMoviesBinding>(R.layout.fragment_movies) {

    override val vm: MoviesViewModel by viewModels()

    private lateinit var moviesAdapter: MoviesAdapter

    override fun initViews() {
        super.initViews()
        moviesAdapter = MoviesAdapter(vm)
        vb.rvMovies.apply {
            adapter = moviesAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
        vb.appBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.most_popular -> vm.changeSortType(SortType.MOST_POPULAR)
                R.id.top_rated -> vm.changeSortType(SortType.TOP_RATED)
                R.id.search -> navigateTo(MoviesFragmentDirections.actionMoviesToSearch())
            }
            false
        }
    }

    override fun setupObservers() {
        super.setupObservers()
        observe(vm.event) {
            onEventReceived(it)
        }
        observe(vm.movies) {
            moviesAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
//        observe(vm.sortType) {
//            vm.fetchMovies(it)
//        }
    }

    private fun onEventReceived(event: MoviesEvent) {
        when (event) {
            is MoviesEvent.MovieClick -> navigateTo(
                MoviesFragmentDirections.actionMoviesToMovieDetails(event.movie.id)
            )
        }
    }
}