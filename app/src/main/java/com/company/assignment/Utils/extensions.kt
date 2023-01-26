package com.company.assignment.Utils

import android.content.Context
import android.content.res.Configuration

/**
 * Determine the size of the screen so we can determine the required size of the image 
 * from the image from json format
 */
fun getSizeName(context: Context): String {
    var screenLayout: Int = context.resources.configuration.screenLayout
    screenLayout = screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK
    return when (screenLayout) {
        Configuration.SCREENLAYOUT_SIZE_SMALL -> "small"
        Configuration.SCREENLAYOUT_SIZE_NORMAL -> "large"
        Configuration.SCREENLAYOUT_SIZE_LARGE -> "extralarge"
        4 -> "extralarge"
        else -> "undefined"
    }
    
    
    
}

