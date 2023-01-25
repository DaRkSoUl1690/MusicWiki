package com.company.assignment.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.glideLoad(s: String) {
    Glide.with(context)
        .load(s)
        .into(this)
}