package com.trianglz.ui.base

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingData

abstract class BasePagingFragment<T : ViewDataBinding, I : BaseIntent, D : Any>(@LayoutRes private val contentLayoutId: Int) :
    BaseFragment<T, I, PagingData<D>>(contentLayoutId) {
    abstract override val vm: BasePagingViewModel<I, D>
}