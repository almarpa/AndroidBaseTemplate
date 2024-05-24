package com.example.androidbasetemplate.ui.pokemonlist.details

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.androidbasetemplate.R
import com.example.androidbasetemplate.common.utils.pokemonSharedElement
import com.example.androidbasetemplate.entity.Pokemon
import com.example.androidbasetemplate.entity.PokemonDetails
import com.example.androidbasetemplate.entity.enums.AppTheme
import com.example.androidbasetemplate.ui.common.mocks.getPokemonDetailsMock
import com.example.androidbasetemplate.ui.common.mocks.getPokemonDetailsViewModelMock
import com.example.androidbasetemplate.ui.common.mocks.getPokemonMock
import com.example.androidbasetemplate.ui.common.mocks.getSettingsViewModelMock
import com.example.androidbasetemplate.ui.common.preview.TemplatePreviewTheme
import com.example.androidbasetemplate.ui.common.topappbar.DefaultTopAppBar
import com.example.androidbasetemplate.ui.favorites.FavouritesViewModel
import com.example.androidbasetemplate.ui.settings.SettingsViewModel
import java.net.URLDecoder
import java.util.Locale

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PokemonDetailsScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    pokemonDetailsViewModel: PokemonDetailsViewModel = hiltViewModel(),
    favouritesViewModel: FavouritesViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    pokemon: Pokemon,
    imageSize: Int = 300,
    navigateBack: () -> Unit = {},
) {
    val pokemonDetails by pokemonDetailsViewModel.pokemonDetails.collectAsStateWithLifecycle()

    BackHandler { navigateBack() }
    PokemonDetailsContent(
        animatedVisibilityScope = animatedVisibilityScope,
        settingsViewModel = settingsViewModel,
        pokemon = pokemon,
        pokemonDetails = pokemonDetails,
        imageSize = imageSize,
        navigateBack = { navigateBack() }
    ) {
        favouritesViewModel.savePokemonToFavourites(pokemon.apply { isFavourite = it })
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PokemonDetailsContent(
    animatedVisibilityScope: AnimatedVisibilityScope,
    settingsViewModel: SettingsViewModel,
    pokemonDetails: PokemonDetails?,
    pokemon: Pokemon,
    imageSize: Int,
    navigateBack: () -> Unit,
    onFavouriteClick: (Boolean) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                getBackgroundColor(
                    settingsViewModel,
                    pokemon.dominantColor?.let { Color(it) } ?: Color.White)
            ),
    ) {
        PokemonDetailTopAppBar { navigateBack() }
        PokemonCard(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = imageSize.dp / 2 - 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 30.dp
                )
                .shadow(10.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            pokemon = pokemon,
            pokemonDetails = pokemonDetails,
            imageSize = imageSize,
        ) {
            onFavouriteClick(it)
        }
        PokemonImageAnimation(animatedVisibilityScope, pokemon, imageSize)
    }
}

@Composable
fun PokemonName(pokemon: Pokemon, onFavouriteClick: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        FavoriteButton(pokemon.isFavourite) { onFavouriteClick(it) }
        Text(
            modifier = Modifier.fillMaxWidth(.9f).padding(vertical = 16.dp),
            text = "${pokemon.id} ${pokemon.name.uppercase(Locale.getDefault())}",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun FavoriteButton(isFavouriteYet: Boolean?, onFavouriteClick: (Boolean) -> Unit) {
    var isFavourite by remember { mutableStateOf(isFavouriteYet ?: false) }
    val favScale by animateFloatAsState(
        targetValue = if (isFavourite) 1.5f else 1f,
        label = "Favourite Button Scale"
    )

    IconButton(
        modifier = Modifier
            .fillMaxWidth(.1f)
            .scale(favScale),
        onClick = {
            isFavourite = !isFavourite
            onFavouriteClick(isFavourite)
        },
    ) {
        Icon(
            modifier = Modifier.fillMaxSize(),
            imageVector = if (isFavourite) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = "Favorite icon",
            tint = if (isFavourite) Color.Red else MaterialTheme.colorScheme.primary,
        )
    }
}

@Preview("Pokemon Details TopAppBar")
@Composable
fun PokemonDetailTopAppBar(navigateBack: () -> Unit = {}) {
    DefaultTopAppBar(
        modifier = Modifier,
        title = R.string.empty_string
    ) {
        navigateBack()
    }
}

@Composable
fun PokemonCard(
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
    pokemonDetails: PokemonDetails?,
    imageSize: Int = 300,
    onFavouriteClick: (Boolean) -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .wrapContentHeight()
            .padding(top = imageSize.dp / 2 + 20.dp)
            .verticalScroll(scrollState)
    ) {
        pokemonDetails?.let {
            PokemonName(pokemon) { onFavouriteClick(it) }
            PokemonType(it.types)
            PokemonMeasures(it.weight, it.height)
            PokemonStats(it)
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PokemonImageAnimation(
    animatedVisibilityScope: AnimatedVisibilityScope,
    pokemon: Pokemon = Pokemon(
        id = 1,
        url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
        name = "Bulbasour"
    ),
    imageSize: Int = 300,
) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .wrapContentHeight()
            .padding(top = 25.dp)
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(URLDecoder.decode(pokemon.url, "UTF-8"))
                .crossfade(true)
                .apply { if (LocalInspectionMode.current) placeholder(R.drawable.pokeball) }
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .size(imageSize.dp)
                .pokemonSharedElement(
                    isLocalInspectionMode = LocalInspectionMode.current,
                    state = rememberSharedContentState(key = "item-image${pokemon.id}"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
        )
    }
}

@Composable
fun getBackgroundColor(settingsViewModel: SettingsViewModel, dominantColor: Color): Brush {
    val userAppTheme by settingsViewModel.themeState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) { settingsViewModel.getUserAppTheme() }

    return when (userAppTheme) {
        AppTheme.AUTO -> if (isSystemInDarkTheme()) {
            getDarkGradient(dominantColor)
        } else {
            getLightGradient(dominantColor)
        }

        AppTheme.DARK -> getDarkGradient(dominantColor)
        AppTheme.LIGHT -> getLightGradient(dominantColor)
    }
}

private fun getLightGradient(dominantColor: Color) =
    Brush.verticalGradient(
        listOf(Color.White, dominantColor),
        startY = 0.0f,
        endY = 400.0f
    )

private fun getDarkGradient(dominantColor: Color) =
    Brush.verticalGradient(
        listOf(Color.Black, dominantColor),
        startY = 0.0f,
        endY = 400.0f
    )


@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview("Pokemon Image Animation")
fun PokemonImageAnimationPreview() {
    TemplatePreviewTheme {
        PokemonImageAnimation(animatedVisibilityScope = it)
    }
}

@Composable
@Preview("Pokemon Card")
@OptIn(ExperimentalSharedTransitionApi::class)
fun PokemonCardPreview() {
    TemplatePreviewTheme {
        PokemonCard(
            pokemonDetails = getPokemonDetailsMock(),
            pokemon = getPokemonMock(),
            onFavouriteClick = { }
        )
    }
}

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview("Pokemon Details Screen")
fun PokemonDetailsScreenPreview() {
    TemplatePreviewTheme {
        PokemonDetailsScreen(
            animatedVisibilityScope = it,
            pokemonDetailsViewModel = getPokemonDetailsViewModelMock(),
            settingsViewModel = getSettingsViewModelMock(),
            pokemon = getPokemonMock(),
            imageSize = 300,
        ) {}
    }
}
