package com.example.androidtemplateapp.ui.common.preview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import com.example.androidtemplateapp.ui.theme.TemplateTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TemplatePreviewTheme(
    content: @Composable SharedTransitionScope.(AnimatedVisibilityScope) -> Unit,
) {
    TemplateTheme {
        SharedTransitionScope {
            AnimatedVisibility(visible = true, label = "") {
                content(this)
            }
        }
    }
}