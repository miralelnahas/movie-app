package com.trianglz.domain.usecases

import com.trianglz.data.managers.ConnectionManager
import javax.inject.Inject

class IsNetworkConnectedUseCase @Inject constructor(private val connectionManager: ConnectionManager) {
    operator fun invoke() = connectionManager.isNetworkAvailable()
}