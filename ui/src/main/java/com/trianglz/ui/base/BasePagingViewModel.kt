package com.trianglz.ui.base

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.trianglz.data.DataState
import com.trianglz.domain.usecases.IsNetworkConnectedUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class BasePagingViewModel<I : BaseIntent, D : Any>(private val isNetworkConnectedUseCase: IsNetworkConnectedUseCase) :
    BaseViewModel<I, PagingData<D>>(isNetworkConnectedUseCase) {

    private val _dataState: MutableStateFlow<DataState<PagingData<D>>> =
        MutableStateFlow(DataState.Empty)
    override val dataState: StateFlow<DataState<PagingData<D>>> = _dataState
    protected fun launchPagingRequest(request: suspend () -> Flow<PagingData<D>>): Job {
        return viewModelScope.launch {
            if (isNetworkConnectedUseCase()) {
                _dataState.value = DataState.Loading
                request().cachedIn(viewModelScope).collectLatest {
                    _dataState.value = DataState.Success(it)
                }
            } else {
                _dataState.value = DataState.Error("")
            }
        }
    }
}