package com.trianglz.ui.base

import androidx.lifecycle.viewModelScope
import com.trianglz.data.DataState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

abstract class BaseUiModel<D : Any, I: BaseIntent>(private val uiModelScope: CoroutineScope) {
    protected val _dataState: MutableStateFlow<DataState<D>> =
        MutableStateFlow(DataState.Empty)
    val dataState: StateFlow<DataState<D>> = _dataState

    val dataIntentChannel = Channel<I>(Channel.BUFFERED)

    init {
        uiModelScope.launch {
            dataIntentChannel.consumeAsFlow().collect {
                onTriggerEvent(it)
            }
        }
    }
    fun <T> Flow<T>.toStateFlow(initValue: T): StateFlow<T> =
        this.stateIn(uiModelScope, SharingStarted.WhileSubscribed(), initValue)

    protected abstract fun onTriggerEvent(eventType: I)

}