package com.ticketswap.assessment.widgets.transitions


import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.util.Property
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionSet
import androidx.transition.TransitionValues

// https://github.com/alexstyl/RadiusTransition/blob/main/app/src/main/java/com/alexstyl/radiustransition/transition/RadiusTransition.kt
class RadiusTransition private constructor(
    private val startingRadius: Float,
    private val endingRadius: Float
) : Transition() {

    override fun captureStartValues(transitionValues: TransitionValues) {
        if (transitionValues.view is ImageView) {
            transitionValues.values[PROPNAME_RADIUS] = startingRadius
        }
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        if (transitionValues.view is ImageView) {
            transitionValues.values[PROPNAME_RADIUS] = endingRadius
        }
    }

    override fun createAnimator(
        sceneRoot: ViewGroup, startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {

        if (startValues == null || endValues == null) {
            return null
        }

        val endImageView = endValues.view as ImageView
        val start = startValues.values[PROPNAME_RADIUS] as Float
        val end = endValues.values[PROPNAME_RADIUS] as Float

        val objectAnimator = ObjectAnimator
            .ofFloat(endImageView, RADIUS_PROPERTY, start, end)
            .setDuration(300)
        objectAnimator.interpolator = super.getInterpolator()
        return objectAnimator
    }

    companion object {

        private const val PROPNAME_RADIUS = "RadiusTransition:radius"

        private val RADIUS_PROPERTY = object : FloatProperty<ImageView>("radius") {

            override fun get(imageView: ImageView): Float {
                val drawable = imageView.drawable as RoundedBitmapDrawable
                return drawable.cornerRadius
            }

            override fun setValue(imageView: ImageView, radius: Float) {
                val drawable = imageView.drawable as? RoundedBitmapDrawable
                if (drawable != null) {
                    drawable.cornerRadius = radius
                    imageView.requestLayout()
                }
            }
        }

        fun toCircle(context: Context): Transition {
            return getDefaultTransactions(context).addTransition(RadiusTransition(0F, 1000F))
        }

        fun toSquare(context: Context): Transition {
            return getDefaultTransactions(context).addTransition(RadiusTransition(1000F, 0F))
        }

        private fun getDefaultTransactions(context: Context): TransitionSet {
            return (TransitionInflater.from(context)
                .inflateTransition(android.R.transition.move) as TransitionSet).apply {
                ordering = TransitionSet.ORDERING_TOGETHER
            }
        }
    }
}

abstract class FloatProperty<T>(name: String?) : Property<T, Float>(Float::class.java, name) {
    abstract fun setValue(`object`: T, value: Float)
    override fun set(`object`: T, value: Float) {
        setValue(`object`, value)
    }
}