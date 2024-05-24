package com.example.androidbasetemplate.ui.favorites

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.androidbasetemplate.R
import com.example.androidbasetemplate.entity.Pokemon
import com.example.androidbasetemplate.ui.common.bottomappbar.TemplateBottomAppBar
import com.example.androidbasetemplate.ui.common.error.GenericRetryView
import com.example.androidbasetemplate.ui.common.loader.FullScreenLoader
import com.example.androidbasetemplate.ui.common.mocks.getFavoritesViewModelMock
import com.example.androidbasetemplate.ui.common.mocks.getPokemonListMock
import com.example.androidbasetemplate.ui.common.navigation.NavigationActions
import com.example.androidbasetemplate.ui.common.navigation.Routes
import com.example.androidbasetemplate.ui.common.preview.TemplatePreviewTheme
import com.example.androidbasetemplate.ui.common.topappbar.DrawerTopAppBar
import java.net.URLDecoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen(
    favouriteListViewModel: FavouritesViewModel = hiltViewModel(),
    drawerState: DrawerState,
    currentRoute: String,
    navigationActions: NavigationActions,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            DrawerTopAppBar(
                drawerState = drawerState,
                title = R.string.favorites_title,
                scrollBehavior = scrollBehavior
            )
        },
        content = {
            val uiState by favouriteListViewModel.uiState.collectAsStateWithLifecycle()
            FavouritesListContent(it, uiState) {
                favouriteListViewModel.getFavourites()
            }
        },
        bottomBar = {
            TemplateBottomAppBar(
                drawerState = drawerState,
                currentRoute = currentRoute,
                navigationActions
            )
        }
    )
}

@Composable
fun FavouritesListContent(
    paddingValues: PaddingValues,
    uiState: FavouritesUiState,
    onRetrySelected: () -> Unit,
) {
    when (uiState) {
        is FavouritesUiState.Loading -> {
            FullScreenLoader()
        }

        is FavouritesUiState.Error -> {
            GenericRetryView { onRetrySelected() }
        }

        is FavouritesUiState.Success -> {
            FavouritesList(paddingValues, pokemonList = uiState.favouriteList)
        }
    }
}

@Composable
fun FavouritesList(paddingValues: PaddingValues, pokemonList: List<Pokemon>) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(pokemonList) { pokemon ->
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(URLDecoder.decode(pokemon.url, "UTF-8"))
                        .crossfade(true)
                        .apply { if (LocalInspectionMode.current) placeholder(R.drawable.pokeball) }
                        .build(),
                    contentDescription = "Pokemon Image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .clip(CircleShape)
                        .fillMaxWidth(.8f)
                        .aspectRatio(1f)
                        .padding(8.dp)
                )

                Text(
                    text = pokemon.name.uppercase(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight(1000),
                    modifier = Modifier
                        .padding(horizontal = 5.dp, vertical = 10.dp),
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
@Preview("Favourites Screen")
fun FavouritesScreenPreview() {
    TemplatePreviewTheme {
        FavouritesScreen(
            favouriteListViewModel = getFavoritesViewModelMock(),
            drawerState = DrawerState(DrawerValue.Closed),
            currentRoute = Routes.Favorites.route,
            navigationActions = NavigationActions(rememberNavController())
        )
    }
}

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview("Pokemon List Content")
fun PokemonListContentPreview() {
    TemplatePreviewTheme {
        FavouritesListContent(
            uiState = FavouritesUiState.Success(getPokemonListMock()),
            paddingValues = PaddingValues(),
        ) {}
    }
}