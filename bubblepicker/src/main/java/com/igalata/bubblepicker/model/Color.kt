package com.igalata.bubblepicker.model

import android.graphics.Color
import androidx.annotation.ColorInt

/**
 * Created by irinagalata on 1/19/17.
 */
data class Color(@ColorInt var color: Int) {

    val red: Float
        get() = Color.red(color) / 256f

    val green: Float
        get() = Color.green(color) / 256f

    val blue: Float
        get() = Color.blue(color) / 256f

    val alpha: Float
        get() = Color.alpha(color) / 256f

}