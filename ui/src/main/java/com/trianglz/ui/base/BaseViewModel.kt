package com.trianglz.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trianglz.data.DataState
import com.trianglz.domain.usecases.IsNetworkConnectedUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

abstract class BaseViewModel<I : BaseIntent, D : Any>(
    private val isNetworkConnectedUseCase: IsNetworkConnectedUseCase
) : ViewModel() {
    val dataIntentChannel = Channel<I>(Channel.BUFFERED)

    private val _dataState: MutableStateFlow<DataState<D>> =
        MutableStateFlow(DataState.Empty)
    open val dataState: StateFlow<DataState<D>> = _dataState

    init {
        viewModelScope.launch {
            dataIntentChannel.consumeAsFlow().collect {
                onTriggerEvent(it)
            }
        }
    }


    fun <T> Flow<T>.toStateFlow(initValue: T): StateFlow<T> =
        this.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), initValue)

    abstract fun onTriggerEvent(eventType: I)

    fun launchRequest(request: suspend () -> Result<D>): Job {
        return viewModelScope.launch {
            if (isNetworkConnectedUseCase()) {
                _dataState.value = DataState.Loading
                request().apply {
                    onSuccess {
                        _dataState.value = DataState.Success(it)
                    }
                    onFailure {
                        _dataState.value = DataState.Error(it.message ?: "")
                    }
                }
            } else {
                _dataState.value = DataState.Error("")
            }
        }
    }
}