package com.example.androidbasetemplate.common.utils

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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
