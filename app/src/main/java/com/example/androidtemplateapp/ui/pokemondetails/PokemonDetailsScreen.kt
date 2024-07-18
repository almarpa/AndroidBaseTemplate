package com.example.androidtemplateapp.ui.pokemondetails

import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.androidtemplateapp.R
import com.example.androidtemplateapp.common.utils.getDarkGradientByColor
import com.example.androidtemplateapp.common.utils.getLightGradientByColor
import com.example.androidtemplateapp.common.utils.pokemonSharedElement
import com.example.androidtemplateapp.entity.Pokemon
import com.example.androidtemplateapp.entity.PokemonDetails
import com.example.androidtemplateapp.entity.enums.AppTheme
import com.example.androidtemplateapp.ui.common.mocks.getPokemonDetailsMock
import com.example.androidtemplateapp.ui.common.mocks.getPokemonMock
import com.example.androidtemplateapp.ui.common.preview.TemplatePreviewTheme
import com.example.androidtemplateapp.ui.common.topappbar.DefaultTopAppBar
import java.net.URLDecoder

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PokemonDetailsScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    pokemon: Pokemon,
    pokemonDetails: PokemonDetails?,
    userAppTheme: AppTheme,
    onAddTeamMember: (Pokemon, Boolean) -> Unit,
    onBackPressed: () -> Unit,
) {
    BackHandler { onBackPressed() }
    PokemonDetailsContent(
        animatedVisibilityScope = animatedVisibilityScope,
        userAppTheme = userAppTheme,
        pokemon = pokemon,
        pokemonDetails = pokemonDetails,
        onAddTeamMember = { isAddedToTeam -> onAddTeamMember(pokemon, isAddedToTeam) },
        onBackPressed = { onBackPressed() }
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PokemonDetailsContent(
    animatedVisibilityScope: AnimatedVisibilityScope,
    userAppTheme: AppTheme,
    pokemonDetails: PokemonDetails?,
    pokemon: Pokemon,
    onAddTeamMember: (Boolean) -> Unit,
    onBackPressed: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                getBackgroundColor(
                    userAppTheme = userAppTheme,
                    dominantColor = pokemon.dominantColor
                )
            ),
    ) {
        PokemonDetailsTopAppBar { onBackPressed() }
        PokemonCard(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 150.dp,
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
        ) { isAdded ->
            onAddTeamMember(isAdded)
        }
        PokemonImageAnimation(animatedVisibilityScope, pokemon)
    }
}

@Preview("Pokemon Details TopAppBar")
@Composable
fun PokemonDetailsTopAppBar(onBackPressed: () -> Unit = {}) {
    DefaultTopAppBar(
        modifier = Modifier,
        title = R.string.empty_string
    ) {
        onBackPressed()
    }
}

@Composable
fun PokemonCard(
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
    pokemonDetails: PokemonDetails?,
    onMemberClick: (Boolean) -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .wrapContentHeight()
            .padding(top = 100.dp)
            .verticalScroll(scrollState)
    ) {
        pokemonDetails?.let { pokemonDetailsNotNull ->
            PokemonName(pokemon) { onMemberClick(it) }
            PokemonType(pokemonDetailsNotNull.types)
            PokemonMeasures(pokemonDetailsNotNull.weight, pokemonDetailsNotNull.height)
            PokemonStats(pokemonDetailsNotNull)
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PokemonImageAnimation(
    animatedVisibilityScope: AnimatedVisibilityScope,
    pokemon: Pokemon,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(URLDecoder.decode(pokemon.url, "UTF-8"))
                .crossfade(true)
                .apply { if (LocalInspectionMode.current) placeholder(R.drawable.pokeball) }
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(.75f)
                .aspectRatio(1f)
                .pokemonSharedElement(
                    isLocalInspectionMode = LocalInspectionMode.current,
                    state = rememberSharedContentState(key = "item-image${pokemon.id}"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
        )
    }
}

@Composable
fun getBackgroundColor(userAppTheme: AppTheme, dominantColor: Int?): Brush {
    val color = dominantColor?.let { Color(it) } ?: Color.White
    return when (userAppTheme) {
        AppTheme.AUTO -> if (isSystemInDarkTheme()) {
            getDarkGradientByColor(color)
        } else {
            getLightGradientByColor(color)
        }

        AppTheme.DARK -> getDarkGradientByColor(color)
        AppTheme.LIGHT -> getLightGradientByColor(color)
    }
}


@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview("Pokemon Image Animation")
fun PokemonImageAnimationPreview() {
    TemplatePreviewTheme {
        PokemonImageAnimation(
            animatedVisibilityScope = it,
            pokemon = getPokemonMock(),
        )
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
            onMemberClick = { }
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
            pokemon = getPokemonMock(),
            pokemonDetails = getPokemonDetailsMock(),
            onAddTeamMember = { _, _ -> },
            onBackPressed = {},
            userAppTheme = AppTheme.DARK
        )
    }
}
