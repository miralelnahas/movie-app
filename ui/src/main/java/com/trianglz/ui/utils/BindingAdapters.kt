package com.trianglz.ui.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.trianglz.ui.R

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("app:goneUnless")
    fun View.goneUnless(visible: Boolean) {
        visibility = if (visible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("app:items")
    fun RecyclerView.setItems(items: List<Nothing>?) {
        setItems(adapter, items)
    }

    private fun <VH : RecyclerView.ViewHolder> setItems(
        adapter: RecyclerView.Adapter<VH>?,
        items: List<Nothing>?
    ) {
        if (adapter is ListAdapter<*, *>) {
            (adapter as? ListAdapter<*, *>)?.submitList(items)
        } else {
            adapter?.notifyDataSetChanged()
        }
    }

    @BindingAdapter("app:loadSrc")
    @JvmStatic
    fun ImageView.loadSrc(srcUrl: String?) {
        val imageUrl = "https://image.tmdb.org/t/p/w185${srcUrl ?: ""}"
        Picasso.get().load(imageUrl)
            .fit().centerCrop()
            .placeholder(R.drawable.img_movie_placeholder)
            .error(R.drawable.img_movie_placeholder)
            .into(this)
    }
}