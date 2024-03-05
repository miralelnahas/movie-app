package com.trianglz.ui.views.search

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trianglz.ui.R
import com.trianglz.ui.base.BaseFragment
import com.trianglz.ui.databinding.FragmentSearchBinding
import com.trianglz.ui.utils.Extensions.observe
import com.trianglz.ui.viewmodels.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    override val vm: SearchViewModel by viewModels()
    private lateinit var moviesAdapter: SearchAdapter

    private val adapterDataObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            if (positionStart == 0) {
                vb.rvMovies.scrollToPosition(0)
            }
        }
    }

    override fun initViews() {
        super.initViews()
        moviesAdapter = SearchAdapter(vm).apply {
            registerAdapterDataObserver(adapterDataObserver)
        }
        vb.rvMovies.apply {
            adapter = moviesAdapter
            layoutManager = GridLayoutManager(context, 2)
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
    }

    private fun onEventReceived(event: SearchEvent) {
        when (event) {
            is SearchEvent.MovieClick -> navigateTo(
                SearchFragmentDirections.actionSearchToMovieDetails(event.movie.id)
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        moviesAdapter.unregisterAdapterDataObserver(adapterDataObserver)
    }
}