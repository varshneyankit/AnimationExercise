package com.assignment.animationexercise

import android.view.animation.Interpolator
import kotlin.math.PI
import kotlin.math.pow
import kotlin.math.sin

internal class MyBounceInterpolator : Interpolator {

    private val factor = 0.3f

    override fun getInterpolation(x: Float): Float {
        return  (2f.pow((-10 * x)) * sin(((2* PI) * (x - (factor/4)))/factor) + 1).toFloat()
    }
}