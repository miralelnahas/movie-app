package com.trianglz.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.trianglz.ui.BR

open class BaseViewHolder(private val viewDataBinding: ViewDataBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    fun <T> bind(vm: T) : ViewDataBinding =
        viewDataBinding.apply {
            setVariable(BR.vm, vm)
            executePendingBindings()
        }

    companion object {
        fun from(parent: ViewGroup, @LayoutRes res: Int): BaseViewHolder {
            val view: ViewDataBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.context), res, parent, false)
            return BaseViewHolder(view)
        }
    }
}