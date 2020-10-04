package com.cb.plus.android.test.utils

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.cb.plus.android.test.R

class ImageUtils {
    fun displayRoundImageFromUrl(
        context: Context,
        url: String?,
        imageView: ImageView
    ) {
        val myOptions = RequestOptions()
            .centerCrop()
            .placeholder(
                ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.ic_launcher_background,
                    null
                )
            )
            .dontAnimate()
        Glide.with(context)
            .asBitmap()
            .apply(myOptions)
            .load(url)
            .into(object : BitmapImageViewTarget(imageView) {
                override fun setResource(resource: Bitmap?) {
                    val circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.resources, resource)
                    circularBitmapDrawable.isCircular = true
                    imageView.setImageDrawable(circularBitmapDrawable)
                }
            })
    }
}