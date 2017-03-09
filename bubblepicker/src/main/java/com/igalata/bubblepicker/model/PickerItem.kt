package com.igalata.bubblepicker.model

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt

/**
 * Created by irinagalata on 1/19/17.
 */
data class PickerItem @JvmOverloads constructor(val title: String,
                                                @ColorInt val color: Int? = null,
                                                val gradient: BubbleGradient? = null,
                                                val overlayAlpha: Float = 0.5f,
                                                val typeface: Typeface = Typeface.DEFAULT,
                                                val textColor: Int,
                                                val image: Drawable,
                                                val isSelected: Boolean = false)