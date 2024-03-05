package com.trianglz.ui.utils

import android.os.Looper
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch


object Extensions {
    fun <E> Fragment.observe(events: Flow<E>, actions: (it: E) -> Unit) {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                events.collect {
                    actions.invoke(it)
                }
            }
        }
    }

}