package com.trianglz.ui.views.movies

import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.trianglz.data.models.movies.SortType
import com.trianglz.ui.MainActivity
import com.trianglz.ui.R
import com.trianglz.ui.base.BaseFragment
import com.trianglz.ui.databinding.FragmentMoviesBinding
import com.trianglz.ui.utils.Extensions.observe
import com.trianglz.ui.viewmodels.movies.MoviesViewModel
import com.trianglz.ui.views.search.SearchEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : BaseFragment<FragmentMoviesBinding>(R.layout.fragment_movies) {

    override val vm: MoviesViewModel by viewModels()

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var searchAdapter: MoviesAdapter

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (vb.searchView.isShowing) {
                vb.searchView.hide()
            } else {
                remove()
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    override fun initViews() {
        super.initViews()
        initSearchViews()
        initMoviesViews()
    }

    private fun initSearchViews() {
        searchAdapter = MoviesAdapter(vm)
        vb.rvMoviesSearch.apply {
            adapter = searchAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
        vb.searchView.editText.addTextChangedListener { text ->
            vm.searchUiModel.updateSearchQuery(text.toString())
        }
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).handleBackPressed(backPressedCallback)
    }

    override fun onPause() {
        super.onPause()
        backPressedCallback.remove()
    }

    private fun initMoviesViews() {
        moviesAdapter = MoviesAdapter(vm)
        vb.rvMovies.apply {
            adapter = moviesAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
        vb.appBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.most_popular -> vm.changeSortType(SortType.MOST_POPULAR)
                R.id.top_rated -> vm.changeSortType(SortType.TOP_RATED)
            }
            false
        }
    }

    override fun setupObservers() {
        super.setupObservers()
        observe(vm.event) {
            onEventReceived(it)
        }
        observe(vm.searchUiModel.event) {
            onEventReceived(it)
        }
        observe(vm.movies) {
            moviesAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
        observe(vm.searchUiModel.movies) {
            searchAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun onEventReceived(event: MoviesEvent) {
        when (event) {
            is MoviesEvent.MovieClick -> navigateTo(
                MoviesFragmentDirections.actionMoviesToMovieDetails(event.movie.id)
            )
        }
    }

    private fun onEventReceived(event: SearchEvent) {
        when (event) {
            is SearchEvent.MovieClick -> navigateTo(
                MoviesFragmentDirections.actionMoviesToMovieDetails(event.movie.id)
            )
        }
    }
}