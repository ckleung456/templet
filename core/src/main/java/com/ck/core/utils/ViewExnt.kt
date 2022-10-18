package com.ck.core.utils

import android.net.Uri
import android.os.SystemClock
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import com.ck.core.R
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.squareup.picasso.Picasso

@SuppressWarnings
fun View.setVisible() = this.apply {
    isVisible = true
}

@SuppressWarnings
fun View.setGone() = this.apply {
    isVisible = false
}

@SuppressWarnings
fun View.setInvisible() = this.apply {
    visibility = View.INVISIBLE
}

@SuppressWarnings
fun ImageView.loadSvgImage(imageUri: String) = this.apply {
    GlideToVectorYou
        .init()
        .with(context)
        .load(Uri.parse(imageUri), this)
}

@SuppressWarnings
fun ImageView.loadImage(
    imageUri: String,
    @DrawableRes placeHolderResId: Int? = null,
    @DrawableRes errorResId: Int? = null
) = this.apply {
    Picasso
        .get()
        .load(imageUri)
        .apply {
            placeHolderResId?.let {
                placeholder(placeHolderResId)
            }
            errorResId?.let {
                error(errorResId)
            }
        }
        .into(this)
}

@SuppressWarnings
fun View.setOnSafeClickListener(throttleInterval: Long? = null, onClickFn: (View?) -> Unit) {
    setOnClickListener(throttleInterval?.let {
        OnSafeClickListener(
            interval = throttleInterval,
            fn = onClickFn
        )
    } ?: OnSafeClickListener(fn = onClickFn))
}

class OnSafeClickListener(
    private val interval: Long = DEFAULT_THROTTLE_TIME,
    private val fn: (View?) -> Unit
) : View.OnClickListener {
    private var lastClickTime = 0L

    override fun onClick(view: View?) {
        val prevClickTime = lastClickTime
        val currClickTime = SystemClock.uptimeMillis()
        lastClickTime = currClickTime
        if (currClickTime - prevClickTime > interval) {
            fn(view)
        }
    }

    companion object {
        private const val DEFAULT_THROTTLE_TIME = 1000L
    }
}