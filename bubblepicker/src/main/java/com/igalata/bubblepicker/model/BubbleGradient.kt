package com.igalata.bubblepicker.model

/**
 * Created by irinagalata on 3/2/17.
 */
data class BubbleGradient @JvmOverloads constructor(val startColor: Int,
                                                    val endColor: Int,
                                                    val direction: Int = HORIZONTAL) {

    companion object {
        const val HORIZONTAL = 0
        const val VERTICAL = 1
    }

}