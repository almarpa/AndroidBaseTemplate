package com.example.androidtemplateapp.common.utils

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode

@Composable
fun <Modifier> Modifier.applyIfCurrentLocalInspectionMode(block: Modifier.() -> Unit) =
    if (LocalInspectionMode.current) {
        this.apply { block() }
    } else {
        this
    }

context(SharedTransitionScope)
@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
fun Modifier.pokemonSharedElement(
    isLocalInspectionMode: Boolean,
    state: SharedTransitionScope.SharedContentState,
    animatedVisibilityScope: AnimatedVisibilityScope,
): Modifier {
    return if (isLocalInspectionMode) {
        this
    } else {
        this.sharedElement(
            state = state,
            animatedVisibilityScope = animatedVisibilityScope,
        )
    }
}

fun Modifier.shimmerLoadingAnimation(
    isLoadingCompleted: Boolean = false,
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
    durationMillis: Int = 1000,
): Modifier {
    return if (isLoadingCompleted) {
        this
    } else {
        composed {
            val shimmerColors = with(Color.White) {
                listOf(
                    copy(alpha = 0.3f),
                    copy(alpha = 0.5f),
                    copy(alpha = 1.0f),
                    copy(alpha = 0.5f),
                    copy(alpha = 0.3f),
                )
            }
            val transition = rememberInfiniteTransition(label = "Shimmer transition animation")
            val translateAnimation = transition.animateFloat(
                initialValue = 0f,
                targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = durationMillis,
                        easing = LinearEasing,
                    ),
                    repeatMode = RepeatMode.Restart,
                ),
                label = "Shimmer loading animation",
            )
            background(
                brush = Brush.linearGradient(
                    colors = shimmerColors,
                    start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
                    end = Offset(x = translateAnimation.value, y = angleOfAxisY),
                ),
            )
        }
    }
}
