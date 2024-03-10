package com.trianglz.ui.views.movies

import android.content.Context
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.search.SearchBar
import com.trianglz.data.DataState
import com.trianglz.data.models.movies.Movie
import com.trianglz.data.models.movies.SortType
import com.trianglz.ui.MainActivity
import com.trianglz.ui.R
import com.trianglz.ui.base.BasePagingFragment
import com.trianglz.ui.databinding.FragmentMoviesBinding
import com.trianglz.ui.utils.Extensions.observe
import com.trianglz.ui.viewmodels.movies.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment :
    BasePagingFragment<FragmentMoviesBinding, MovieIntent, Movie>(R.layout.fragment_movies) {

    override val vm: MoviesViewModel by viewModels()

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var searchAdapter: MoviesAdapter

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            vb.layoutMovies.searchView.let {
                if (it.isShowing) {
                    it.hide()
                } else {
                    remove()
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
            }
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sendIntent(MovieIntent.LoadMovies)
        sendSearchIntent(SearchIntent.SearchMovies)
    }

    private fun sendSearchIntent(intent: SearchIntent) {
        lifecycleScope.launch {
            vm.searchUiModel.dataIntentChannel.send(intent)
        }
    }

    override fun initViews() {
        super.initViews()
        initSearchViews()
        initMoviesViews()
    }

    override fun setupObservers() {
        super.setupObservers()
        observe(vm.dataState) {
            onMoviesStateChanged(it)
        }
        observe(vm.searchUiModel.dataState) {
            onSearchStateChanged(it)
        }
    }

    private fun initSearchViews() {
        searchAdapter = MoviesAdapter { movie ->
            navigateTo(MoviesFragmentDirections.actionMoviesToMovieDetails(movie.movieId))
        }
        vb.layoutMovies.apply {
            rvMoviesSearch.apply {
                adapter = searchAdapter
                layoutManager = GridLayoutManager(context, 2)
            }
            searchView.editText.addTextChangedListener { text ->
                sendSearchIntent(SearchIntent.UpdateSearchQuery(text.toString()))
            }
        }
        searchAdapter.addLoadStateListener {
            if (it.refresh is LoadState.NotLoading && it.prepend.endOfPaginationReached && !it.append.endOfPaginationReached) {
                vb.layoutMovies.rvMoviesSearch.scrollToPosition(0)
            }
        }
    }

    private fun initMoviesViews() {
        moviesAdapter = MoviesAdapter { movie ->
            navigateTo(MoviesFragmentDirections.actionMoviesToMovieDetails(movie.movieId))
        }
        vb.layoutMovies.apply {
            rvMovies.apply {
                adapter = moviesAdapter
                layoutManager = GridLayoutManager(context, 2)
            }
            handleMenuItemClick(appBar)
        }
    }

    private fun handleMenuItemClick(appBar: SearchBar) {
        appBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.most_popular -> onMenuItemClick(SortType.MOST_POPULAR)

                R.id.top_rated -> onMenuItemClick(SortType.TOP_RATED)
            }
            false
        }
    }

    private fun onMenuItemClick(sortType: SortType) {
        sendIntent(MovieIntent.ChangeSortType(sortType))
        updateMenuItemIcon(sortType)
    }

    private fun updateMenuItemIcon(selectedSortType: SortType) {
        val sortMenu = vb.layoutMovies.appBar.menu.findItem(R.id.sort).subMenu
        sortMenu?.forEach { sortMenuItem ->
            sortMenuItem.icon = ContextCompat.getDrawable(
                requireContext(),
                if (isMenuItemEqualsSortType(selectedSortType, sortMenuItem))
                    R.drawable.ic_radio_button_selected
                else R.drawable.ic_radio_button_unselected
            )
        }
    }

    private fun isMenuItemEqualsSortType(selectedSortType: SortType, sortMenuItem: MenuItem) =
        (selectedSortType == SortType.MOST_POPULAR && sortMenuItem.itemId == R.id.most_popular)
                || (selectedSortType == SortType.TOP_RATED && sortMenuItem.itemId == R.id.top_rated)

    private fun onMoviesStateChanged(state: DataState<PagingData<Movie>>) {
        when (state) {
            is DataState.Loading -> {
                startLoader()
            }

            is DataState.Success -> {
                stopLoader()
                moviesAdapter.submitData(viewLifecycleOwner.lifecycle, state.data)
            }

            is DataState.Error -> {
                stopLoader()
            }

            is DataState.Empty -> {
                //TODO: fix sort when clicking back after opening movie from top_rated
                updateMenuItemIcon(SortType.MOST_POPULAR)
            }

            else -> {}
        }
    }

    private fun onSearchStateChanged(state: DataState<PagingData<Movie>>) {
        when (state) {
            is DataState.Success -> {
                searchAdapter.submitData(viewLifecycleOwner.lifecycle, state.data)
            }

            //TODO: handle network error
            else -> {}
        }
    }

    private fun startLoader() {
        vb.layoutLoader.shimmerContainer.apply {
            postDelayed(
                {
                    startShimmer()
                }, LOADER_DELAY
            )
        }
    }

    private fun stopLoader() {
        vb.layoutLoader.shimmerContainer.apply {
            postDelayed({
                stopShimmer()
            }, LOADER_DELAY)
        }
    }

    companion object {
        private const val LOADER_DELAY = 500L
    }
}