package com.igalata.bubblepicker.model

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt

/**
 * Created by irinagalata on 1/19/17.
 */
data class PickerItem @JvmOverloads constructor(val title: String? = null,
                                                val icon: Drawable? = null,
                                                val iconOnTop: Boolean = true,
                                                @ColorInt val color: Int? = null,
                                                val gradient: BubbleGradient? = null,
                                                val overlayAlpha: Float = 0.5f,
                                                val typeface: Typeface = Typeface.DEFAULT,
                                                @ColorInt val textColor: Int? = null,
                                                val textSize: Float = 40f,
                                                val backgroundImage: Drawable? = null,
                                                val isSelected: Boolean = false)