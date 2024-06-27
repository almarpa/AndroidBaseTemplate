package com.example.androidtemplateapp.ui.common.lazylist

import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect

@Composable
fun rememberLazyScrollState(
    visibleItems: Pair<Int, Int>,
    onDisposeItems: (Pair<Int, Int>) -> Unit,
) =
    rememberLazyGridState(visibleItems.first, visibleItems.second).apply {
        DisposableEffect(key1 = Unit) {
            onDispose {
                onDisposeItems(firstVisibleItemIndex to firstVisibleItemScrollOffset)
            }
        }
    }