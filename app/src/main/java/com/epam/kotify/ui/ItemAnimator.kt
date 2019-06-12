package com.epam.kotify.ui

import android.view.View
import android.view.animation.AlphaAnimation

/**
 * ViewHolders animator.
 *
 * @author Vlad Korotkevich
 */

class ItemAnimator {

    private companion object {
        private const val DEFAULT_FROM_ALPHA = 0f
        private const val DEFAULT_TO_ALPHA = 1.0f
        private const val DEFAULT_DURATION: Long = 300
    }

    var fromAlpha = 0f
        set(value) {
            field = if (value > 0f) value else DEFAULT_FROM_ALPHA
        }

    var toAlpha = 1.0f
        set(value) {
            field = if (value > 0f) value else DEFAULT_TO_ALPHA
        }

    var duration: Long = 1000
        set(value) {
            field = if (value > 0) value else DEFAULT_DURATION
        }

    fun fadeInAnimation(view: View) {
        val anim = AlphaAnimation(fromAlpha, toAlpha).also {
            it.duration = duration
        }
        view.startAnimation(anim)
    }
}