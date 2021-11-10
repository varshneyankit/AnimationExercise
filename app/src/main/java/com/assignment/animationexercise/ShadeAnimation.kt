package com.assignment.animationexercise

import android.animation.ValueAnimator
import android.content.Context
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.animation.OvershootInterpolator
import android.widget.RelativeLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.animation.addListener
import java.lang.Math.toRadians
import kotlin.math.cos
import kotlin.math.sin

class ShadeAnimation(private val context: Context, private val root: RelativeLayout) {

    private var fixed120InRadian: Float=-1f
    private var viewG: RelativeLayout? = null

    private var centerX: Int = -1
    private var centerY: Int = -1
    private var radi: Int = 0

    private var viewInfo: MutableList<Float> = ArrayList()

    fun initO() {
        initAttributes(root)

        if (viewG == null) {
            viewG = RelativeLayout(root.context)
            viewG?.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            root.addView(viewG)
        } else {
            viewG?.removeAllViews()
        }
        startAnimation()
    }

    private fun initAttributes(main: ViewGroup) {
        centerX = (main.width / 2)
        centerY = (main.height / 2)
        radi = (main.width * 0.3).toInt()

        //-90,270
        //60,420
        //120,480
        val temp = mutableListOf(-90.0,30.0,150.0)
        for (t in temp) {
            viewInfo.add(toRadians(t).toFloat())
            fixed120InRadian=toRadians(120.0).toFloat()
        }
    }

    private fun startAnimation() {

        val colors = listOf(
            context.getColor(R.color.teal_200),
            context.getColor(R.color.purple_200),
            context.getColor(R.color.red_300),
            context.getColor(R.color.red_700)
        )
        for (i in 0..3) {
            val view = View(root.context)
            view.background =
                AppCompatResources.getDrawable(context, R.drawable.circle_image)
            view.background.setTint(colors[i])
            view.layoutParams = ViewGroup.LayoutParams(100, 100)
            view.requestLayout()
            viewG?.addView(view)
            if (i == 3) {
                view.visibility = View.INVISIBLE
                Handler().postDelayed(
                    {
                        view.layoutParams.width = 500
                        view.layoutParams.height = 500
                        view.x = centerX.toFloat() - 250
                        view.y = centerY.toFloat() - 250
                        view.visibility = View.VISIBLE
                        val animator =
                            ValueAnimator.ofFloat(0f, 1f)
                        animator.addUpdateListener { animation ->
                            val v = animation.animatedValue as Float
                            view.alpha = v
                        }
                        animator.duration = 300
                        animator.start()
                    },
                    4400
                )
            } else
                animate(view, i, 0)
        }

    }

    private fun animate(view: View, i: Int, stage: Int) {
        val startingAngle=viewInfo[i]
        viewInfo[i]=viewInfo[i]+fixed120InRadian
        val animator =
            ValueAnimator.ofFloat(startingAngle, viewInfo[i])

        animator.addUpdateListener { animation ->
            //Log.e("TAG", "animate: stop" )
            val angle = animation.animatedValue as Float
            view.x = centerX + radi * cos(angle) - 50
            view.y = centerY + radi * sin(angle) - 50
            //  Log.e("TAG", "animate: view x ${animation.animatedValue} \t ${animation.animatedFraction}" )
        }
        animator.duration = 700
        //animator.interpolator = MyBounceInterpolator()
        animator.interpolator = OvershootInterpolator(1.5f)
       /* animator.repeatMode = ValueAnimator.RESTART
        animator.repeatCount = 0*/

        animator.addListener(onEnd = {
            if (stage == 3)
                startEndAnimation(view)
            else
                animate(view, i, stage + 1)
        })
        animator.start()

    }

    private fun startEndAnimation(view: View) {
        val animatorEnd =
            ValueAnimator.ofInt(100, 500)
        val viewX = view.x
        val viewY = view.y
        val dx = viewX - centerX
        val dy = viewY - centerY
        animatorEnd.addUpdateListener { animation ->
            val v = animation.animatedValue as Int
            view.layoutParams.width = v
            view.layoutParams.height = v
            view.x = viewX - (dx + v / 2) * animation.animatedFraction
            view.y = viewY - (dy + v / 2) * animation.animatedFraction
            view.requestLayout()
        }
        animatorEnd.duration = 1800
        animatorEnd.start()
    }
}