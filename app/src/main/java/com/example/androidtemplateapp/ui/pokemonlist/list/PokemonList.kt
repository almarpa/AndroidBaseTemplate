package com.example.androidtemplateapp.ui.pokemonlist.list

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidtemplateapp.common.anim.getLazyGridAnimation
import com.example.androidtemplateapp.common.utils.applyIfCurrentLocalInspectionMode
import com.example.androidtemplateapp.entity.Pokemon
import com.example.androidtemplateapp.ui.common.lazyscroll.rememberLazyScrollState
import com.example.androidtemplateapp.ui.common.mocks.getPokemonListMock
import com.example.androidtemplateapp.ui.common.preview.TemplatePreviewTheme
import com.example.androidtemplateapp.ui.common.spacer.CustomSpacer

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PokemonList(
    animatedVisibilityScope: AnimatedVisibilityScope,
    pokemonList: List<Pokemon>,
    visibleItems: Pair<Int, Int>,
    onDisposeItems: (Pair<Int, Int>) -> Unit = { },
    onPokemonItemClick: (Pokemon) -> Unit = { },
) {
    val currentOrientation = LocalConfiguration.current.orientation
    val columns = if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) 3 else 2

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        state = rememberLazyScrollState(
            visibleItems = visibleItems,
            onDisposeItems = { onDisposeItems(it) }
        ),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
    ) {
        items(count = pokemonList.size, key = { pokemonList[it].id }) { index ->
            with(getLazyGridAnimation(index, columns)) {
                PokemonItem(
                    modifier = Modifier.applyIfCurrentLocalInspectionMode {
                        graphicsLayer(alpha = first, scaleX = second, scaleY = second)
                    },
                    animatedVisibilityScope = animatedVisibilityScope,
                    pokemon = pokemonList[index],
                    onPokemonItemClick = { onPokemonItemClick(it) },
                )
                CustomSpacer(height = 16, width = 16)
            }
        }
    }
}

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview("Pokemon List")
@Preview("Pokemon List", uiMode = Configuration.UI_MODE_NIGHT_YES)
fun PokemonListPreview() {
    TemplatePreviewTheme {
        PokemonList(
            animatedVisibilityScope = it,
            pokemonList = getPokemonListMock(),
            visibleItems = 0 to 0,
            onPokemonItemClick = { },
            onDisposeItems = { },
        )
    }
}
