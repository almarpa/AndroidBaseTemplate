package com.example.androidbasetemplate.common.utils

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
fun Modifier.getModifierWithSharedElementAnimationOrDefault(
    modifier: Modifier = this,
    sharedTransitionScope: SharedTransitionScope?,
    animatedContentScope: AnimatedContentScope?,
    elementPosition: Int,
) =
    sharedTransitionScope?.let {
        animatedContentScope?.let {
            with(sharedTransitionScope) {
                modifier.sharedElement(
                    state = rememberSharedContentState(key = "item-image${elementPosition}"),
                    animatedVisibilityScope = animatedContentScope
                )
            }
        } ?: modifier
    } ?: modifier
