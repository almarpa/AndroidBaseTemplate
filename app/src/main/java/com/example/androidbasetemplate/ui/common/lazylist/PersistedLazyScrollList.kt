package com.example.androidbasetemplate.ui.common.lazylist

import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.example.androidbasetemplate.ui.pokemonlist.PokemonListViewModel

@Composable
fun rememberLazyScrollState(viewModel: PokemonListViewModel?) =
    rememberLazyGridState(
        viewModel?.firstVisibleItemIdx ?: 0,
        viewModel?.firstVisibleItemOffset ?: 0,
    ).apply {
        DisposableEffect(key1 = null) {
            onDispose {
                viewModel?.firstVisibleItemIdx = firstVisibleItemIndex
                viewModel?.firstVisibleItemOffset = firstVisibleItemScrollOffset
            }
        }
    }