package com.example.androidtemplateapp.common.utils

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
