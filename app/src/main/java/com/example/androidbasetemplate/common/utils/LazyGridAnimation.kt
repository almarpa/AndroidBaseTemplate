package com.example.androidbasetemplate.common.utils

import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember


private enum class State { PLACING, PLACED }

data class ScaleAndAlphaAnimationArgs(
    val fromScale: Float,
    val toScale: Float,
    val fromAlpha: Float,
    val toAlpha: Float,
)

@Composable
fun getLazyGridAnimation(index: Int, columns: Int) =
    scaleAndAlphaAnimation(
        args = ScaleAndAlphaAnimationArgs(
            fromScale = 0f,
            toScale = 1f,
            fromAlpha = 0f,
            toAlpha = 1f
        ),
        animation = tween(
            durationMillis = 400,
            delayMillis = index % columns * 50,
            easing = LinearOutSlowInEasing
        )
    )


@Composable
private fun scaleAndAlphaAnimation(
    args: ScaleAndAlphaAnimationArgs,
    animation: FiniteAnimationSpec<Float>,
): Pair<Float, Float> {
    val transitionState =
        remember { MutableTransitionState(State.PLACING).apply { targetState = State.PLACED } }
    val transition = rememberTransition(transitionState)
    val alpha by transition.animateFloat(transitionSpec = { animation }, label = "") { state ->
        when (state) {
            State.PLACING -> args.fromAlpha
            State.PLACED -> args.toAlpha
        }
    }
    val scale by transition.animateFloat(transitionSpec = { animation }, label = "") { state ->
        when (state) {
            State.PLACING -> args.fromScale
            State.PLACED -> args.toScale
        }
    }
    return alpha to scale
}