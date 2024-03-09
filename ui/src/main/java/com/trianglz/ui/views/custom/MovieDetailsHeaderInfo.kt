package com.trianglz.ui.views.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.trianglz.ui.R

class MovieDetailsHeaderInfo @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null
) : ConstraintLayout(context, attributes) {

    init {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_movie_details_header_info, this)
        context.theme.obtainStyledAttributes(
            attributes,
            R.styleable.MovieDetailsHeaderInfo,
            0, 0
        ).apply {
            try {
                val label = getString(R.styleable.MovieDetailsHeaderInfo_label)
                val icon = getDrawable(R.styleable.MovieDetailsHeaderInfo_icon)
                findViewById<TextView>(R.id.tvLabel).text = label
                findViewById<ImageView>(R.id.ivIcon).setImageDrawable(icon)
            } finally {
                recycle()
            }
        }
    }

    companion object {

        fun MovieDetailsHeaderInfo.setValue(value: String?) {
            findViewById<TextView>(R.id.tvValue).text = value
        }
    }
}