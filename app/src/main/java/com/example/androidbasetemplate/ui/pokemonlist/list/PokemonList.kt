package com.example.androidbasetemplate.ui.pokemonlist.list

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.androidbasetemplate.common.utils.getLazyGridAnimation
import com.example.androidbasetemplate.domain.impl.FakePokemonUseCaseImpl
import com.example.androidbasetemplate.entity.Pokemon
import com.example.androidbasetemplate.ui.common.lazylist.rememberLazyScrollState
import com.example.androidbasetemplate.ui.common.preview.TemplatePreviewTheme
import com.example.androidbasetemplate.ui.common.spacer.CustomSpacer
import com.example.androidbasetemplate.ui.pokemonlist.PokemonListViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PokemonList(
    animatedVisibilityScope: AnimatedVisibilityScope,
    pokemonList: List<Pokemon>,
    pokemonListViewModel: PokemonListViewModel = hiltViewModel(),
    onPokemonItemClick: (Pokemon) -> Unit = { },
) {
    val currentOrientation = LocalConfiguration.current.orientation
    val columns = if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) 3 else 2

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
            with(getLazyGridAnimation(index, columns)) {
                PokemonItem(
                    modifier = Modifier.graphicsLayer(
                        alpha = first,
                        scaleX = second,
                        scaleY = second
                    ),
                    animatedVisibilityScope = animatedVisibilityScope,
                    pokemon = pokemonList[index],
                ) { pokemonItem ->
                    onPokemonItemClick(pokemonItem)
                }
                CustomSpacer(16, 16)
            }
        }
    }
}

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview("Pokemon Image Animation")
fun PokemonListPreview() {
    TemplatePreviewTheme {
        PokemonList(
            animatedVisibilityScope = it,
            pokemonList = listOf(
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
                )
            ),
            pokemonListViewModel = PokemonListViewModel(FakePokemonUseCaseImpl())
        )
    }
}
