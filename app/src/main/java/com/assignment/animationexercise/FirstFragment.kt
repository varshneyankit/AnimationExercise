package com.assignment.animationexercise

import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.addListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.assignment.animationexercise.databinding.FragmentFirstBinding


class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val shadeAnimation = ShadeAnimation(requireContext(), binding.main)
        binding.fab.setOnClickListener {
            shadeAnimation.initO()
        }
        binding.buttonFirst.setOnClickListener {
            //expand(binding.buttonFirst,500,binding.main.height)
            //it.isEnabled = false
            binding.buttonFirst.animate()
                .scaleXBy(10f)
                .scaleYBy(40f)
                //.translationX((200).toFloat()).translationY((-binding.main.height).toFloat())
                .alpha(0.5f)
                .withEndAction { findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment) }
                .duration = 300 // all take 1 seconds
        }
        Handler().postDelayed(
            { shadeAnimation.initO() },
            2000
        )
    }

    fun expand(v: View, duration: Int, targetHeight: Int) {
        val prevHeight = v.height
        v.visibility = View.VISIBLE
        val valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight)
        valueAnimator.addUpdateListener { animation ->
            v.layoutParams.height = animation.animatedValue as Int
            v.layoutParams.width = animation.animatedValue as Int
            v.requestLayout()
        }
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.duration = duration.toLong()
        valueAnimator.addListener(onEnd = {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        })
        valueAnimator.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}