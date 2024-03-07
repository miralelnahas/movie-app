package com.trianglz.ui.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

abstract class BaseUiModel(private val uiModelScope: CoroutineScope) {
    fun <T> Flow<T>.toStateFlow(initValue: T): StateFlow<T> =
        this.stateIn(uiModelScope, SharingStarted.WhileSubscribed(), initValue)

    fun <T> sendEvent(channel: Channel<T>, event: T) {
        uiModelScope.launch {
            channel.send(event)
        }
    }
}