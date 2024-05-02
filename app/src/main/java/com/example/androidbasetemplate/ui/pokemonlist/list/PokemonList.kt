package com.example.androidbasetemplate.ui.pokemonlist.list

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidbasetemplate.common.utils.ScaleAndAlphaAnimation
import com.example.androidbasetemplate.common.utils.ScaleAndAlphaAnimationArgs
import com.example.androidbasetemplate.entity.Pokemon
import com.example.androidbasetemplate.ui.common.NavigationActions
import com.example.androidbasetemplate.ui.common.lazylist.rememberLazyScrollState
import com.example.androidbasetemplate.ui.pokemonlist.PokemonListViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@Preview(
    name = "Pokemon List View",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Pokemon List View Landscape",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = "spec:width=500dp,height=500dp,orientation=landscape"
)
fun PokemonList(
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
    pokemonList: List<Pokemon> = listOf(
        Pokemon(
            id = 1,
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
            name = "Pokemon 1",
        ),
        Pokemon(
            id = 2,
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/2.png",
            name = "Pokemon 2",
        ),
        Pokemon(
            id = 3,
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/3.png",
            name = "Pokemon 3",
        ),
        Pokemon(
            id = 4,
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/2.png",
            name = "Pokemon 4",
        ),
        Pokemon(
            id = 5,
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/3.png",
            name = "Pokemon 5",
        ),
        Pokemon(
            id = 6,
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/3.png",
            name = "Pokemon 6",
        ),
    ),
    pokemonListViewModel: PokemonListViewModel? = null,
    navigationActions: NavigationActions? = null,
    onPokemonItemClick: (Pair<Int, Color>) -> Unit = { },
) {

    val currentOrientation = LocalConfiguration.current.orientation
    val isLandScape by remember {
        mutableStateOf(currentOrientation == Configuration.ORIENTATION_LANDSCAPE)
    }
    val columns = if (isLandScape) 2 else 1

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        state = rememberLazyScrollState(pokemonListViewModel),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
    ) {
        items(count = pokemonList.size) { index ->
            val (scale, alpha) = getScaleAndAlphaAnimation(index, columns)

            PokemonItem(
                modifier = Modifier.graphicsLayer(alpha = alpha, scaleX = scale, scaleY = scale),
                pokemonList[index],
                sharedTransitionScope,
                animatedContentScope,
            ) { pokemonDetails ->
                onPokemonItemClick(pokemonDetails)
            }
            Spacer(
                modifier = Modifier
                    .width(16.dp)
                    .height(16.dp)
            )
        }
    }
}

@Composable
fun getScaleAndAlphaAnimation(index: Int, columns: Int) =
    ScaleAndAlphaAnimation(
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
