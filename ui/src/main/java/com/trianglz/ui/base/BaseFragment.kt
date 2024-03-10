package com.trianglz.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.trianglz.ui.BR
import kotlinx.coroutines.launch

abstract class BaseFragment<T : ViewDataBinding, I : BaseIntent, D : Any>(@LayoutRes private val contentLayoutId: Int) :
    Fragment() {
    protected abstract val vm: BaseViewModel<I, D>
    private var _vb: T? = null
    protected val vb get() = _vb!!

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val viewDataBinding: ViewDataBinding = DataBindingUtil.inflate<ViewDataBinding?>(
            LayoutInflater.from(context), contentLayoutId, container, false
        ).apply {
            this@BaseFragment._vb = this as T
            setViewBinding(this)
            lifecycleOwner = viewLifecycleOwner
        }

        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        initViews()
    }

    open fun initViews() {
        /*
           The class that will extend BaseFragment can override this function
           and there is no specific or common implementation to be placed here
        */
    }

    open fun setViewBinding(vb: T) {
        vb.setVariable(BR.vm, vm)
    }

    open fun setupObservers() {

    }

    protected fun onBackPressed() {
        activity?.onBackPressedDispatcher?.onBackPressed()
    }

    protected fun showToast(message: String?) =
        Toast.makeText(context, message ?: "", Toast.LENGTH_LONG).show()

    override fun onDestroy() {
        super.onDestroy()
        _vb = null
    }

    fun navigateTo(navDirections: NavDirections) {
        try {
            findNavController().navigate(navDirections)
        } catch (e: Exception) {

        }
    }

    fun sendIntent(intent: I) {
        lifecycleScope.launch {
            vm.dataIntentChannel.send(intent)
        }
    }

}