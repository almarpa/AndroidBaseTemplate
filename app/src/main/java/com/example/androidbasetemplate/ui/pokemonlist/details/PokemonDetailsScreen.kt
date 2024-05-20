package com.example.androidbasetemplate.ui.pokemonlist.details

import android.content.res.Configuration
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.androidbasetemplate.entity.*
import com.example.androidbasetemplate.entity.enums.AppTheme
import com.example.androidbasetemplate.entity.enums.PokemonTypeEnum
import com.example.androidbasetemplate.entity.enums.StatNameEnum
import com.example.androidbasetemplate.ui.common.preview.TemplatePreviewTheme
import com.example.androidbasetemplate.ui.common.topappbar.DefaultTopAppBar
import com.example.androidbasetemplate.ui.settings.SettingsViewModel
import java.net.URLDecoder
import java.util.Locale

@OptIn(ExperimentalSharedTransitionApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SharedTransitionScope.PokemonDetailsScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    pokemon: Pokemon = Pokemon(1, "", "name"),
    imageSize: Int = 300,
    navigateBack: () -> Unit = {},
) {
    BackHandler { navigateBack() }
    PokemonDetailsContent(
        animatedVisibilityScope = animatedVisibilityScope,
        pokemon = pokemon,
        imageSize = imageSize
    ) {
        navigateBack()
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PokemonDetailsContent(
    animatedVisibilityScope: AnimatedVisibilityScope,
    pokemonDetailsViewModel: PokemonDetailsViewModel = hiltViewModel(),
    pokemon: Pokemon = Pokemon(1, "", "name"),
    imageSize: Int = 300,
    navigateBack: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                getBackgroundColor(pokemon.dominantColor?.let { Color(it) } ?: Color.White)
            ),
    ) {
        val pokemonDetails by pokemonDetailsViewModel.pokemonDetails.observeAsState()
        LaunchedEffect(Unit) { pokemonDetailsViewModel.getPokemonDetails(pokemon.id) }

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
            pokemonDetails = pokemonDetails,
            pokemon = pokemon,
            imageSize = imageSize,
        )
        PokemonImageAnimation(animatedVisibilityScope, pokemon, imageSize)
    }
}

@Preview("Pokemon Details TopAppBar", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PokemonDetailTopAppBar(navigateBack: () -> Unit = {}) {
    DefaultTopAppBar(
        modifier = Modifier,
        title = R.string.empty_string
    ) {
        navigateBack()
    }
}

@Preview("Pokemon Card", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PokemonCard(
    modifier: Modifier = Modifier,
    pokemon: Pokemon = Pokemon(1, "", "name"),
    imageSize: Int = 300,
    pokemonDetails: PokemonDetails? = PokemonDetails(
        id = 1,
        order = 1,
        name = "Bulbasour",
        baseExperience = 64,
        height = 24,
        weight = 12,
        imageURL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
        stats = listOf(
            Stat(
                baseStat = 50, effort = 30, statX = StatX(StatNameEnum.ATTACK, "")
            ),
            Stat(
                baseStat = 80, effort = 70, statX = StatX(StatNameEnum.DEFENSE, "")
            ),
            Stat(
                baseStat = 70, effort = 10, statX = StatX(StatNameEnum.SPECIAL_ATTACK, "")
            ),
            Stat(
                baseStat = 100, effort = 30, statX = StatX(StatNameEnum.SPECIAL_DEFENSE, "")
            )
        ),
        types = listOf(
            TypeX(
                slot = 1, typeXX = TypeXX(PokemonTypeEnum.BUG, "")
            ), TypeX(
                slot = 2, typeXX = TypeXX(PokemonTypeEnum.POISON, "")
            )
        )
    ),
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
            Text(
                text = "${it.id} ${it.name.uppercase(Locale.getDefault())}",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
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
fun getBackgroundColor(dominantColor: Color): Brush {
    val settingsViewModel: SettingsViewModel = hiltViewModel()
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
fun PokemonItemPreview() {
    TemplatePreviewTheme {
        PokemonImageAnimation(animatedVisibilityScope = it)
    }
}
