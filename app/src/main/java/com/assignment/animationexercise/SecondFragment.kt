package com.assignment.animationexercise

import android.animation.ValueAnimator
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.Transformation
import androidx.core.animation.addListener
import androidx.core.graphics.component2
import androidx.core.graphics.component4
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.assignment.animationexercise.databinding.FragmentSecondBinding


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
           // collapse(binding.secondFragmentRoot,1000,100)
            binding.secondFragmentRoot.animate()
                .scaleX(0.1f).scaleY(0.2f)
                .translationY((view.height/2).toFloat())
                .alpha(0.1f).withEndAction { findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment) }
                .duration = 300 // all take 1 seconds
        }
    }

    private fun collapse(v: View, duration: Int, targetHeight: Int) {
        val prevHeight = v.height
        v.x = (v.width / 2).toFloat()
        v.y = (v.height).toFloat()
        val centerY = v.height

        val valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight)
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.addUpdateListener { animation ->
            v.layoutParams.height = animation.animatedValue as Int
            v.layoutParams.width = animation.animatedValue as Int
            v.requestLayout()
        }
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.duration = duration.toLong()
        valueAnimator.addListener(onEnd = {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        })
        valueAnimator.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}