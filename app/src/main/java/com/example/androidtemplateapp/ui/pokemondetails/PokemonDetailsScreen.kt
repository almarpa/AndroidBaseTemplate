package com.example.androidtemplateapp.ui.pokemondetails

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.androidtemplateapp.R
import com.example.androidtemplateapp.common.utils.ObserveAsEvents
import com.example.androidtemplateapp.common.utils.getBackgroundColorWithGradient
import com.example.androidtemplateapp.common.utils.pokemonSharedElement
import com.example.androidtemplateapp.entity.Pokemon
import com.example.androidtemplateapp.entity.PokemonDetails
import com.example.androidtemplateapp.entity.enums.AppTheme
import com.example.androidtemplateapp.ui.common.mocks.getPokemonDetailsMock
import com.example.androidtemplateapp.ui.common.mocks.getPokemonMock
import com.example.androidtemplateapp.ui.common.preview.TemplatePreviewTheme
import com.example.androidtemplateapp.ui.common.snackbar.CustomSnackBar
import com.example.androidtemplateapp.ui.common.snackbar.SnackbarController
import com.example.androidtemplateapp.ui.common.snackbar.SnackbarEvent
import com.example.androidtemplateapp.ui.common.snackbar.showSnackbar
import com.example.androidtemplateapp.ui.common.spacer.CustomSpacer
import com.example.androidtemplateapp.ui.common.topappbar.DefaultTopAppBar
import com.example.androidtemplateapp.ui.common.utils.isTablet
import kotlinx.coroutines.launch
import java.net.URLDecoder

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val snackbarMessage = LocalContext.current.getString(R.string.pokemon_added_to_team)

    ObserveAsEvents(
        flow = SnackbarController.snackbarEvents,
        key1 = snackbarHostState,
    ) { snackbarEvent ->
        coroutineScope.showSnackbar(
            snackbarHostState = snackbarHostState,
            message = snackbarEvent.message,
            actionLabel = snackbarEvent.action?.name,
            onActionPerformed = { snackbarEvent.action?.action?.invoke() },
        )
    }

    BackHandler { onBackPressed() }

    Scaffold(
        topBar = { DefaultTopAppBar(title = R.string.empty_string) { onBackPressed() } },
        snackbarHost = { CustomSnackBar(snackbarHostState = snackbarHostState) }
    ) {
        PokemonDetailsContent(
            userAppTheme = userAppTheme,
            pokemon = pokemon,
            pokemonDetails = pokemonDetails,
            animatedVisibilityScope = animatedVisibilityScope,
        ) { pokemon, isAdded ->
            onAddTeamMember(pokemon, isAdded)
            coroutineScope.launch {
                if (isAdded) {
                    SnackbarController.sendSnackbarEvent(event = SnackbarEvent(snackbarMessage))
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.PokemonDetailsContent(
    userAppTheme: AppTheme,
    pokemon: Pokemon,
    pokemonDetails: PokemonDetails?,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onAddTeamMember: (Pokemon, Boolean) -> Unit,
) {
    val scrollState = rememberScrollState()
    val topPaddingCard = 100

    // Minimize image when user scrolls and landscape is active
    val animatedImageSize by animateDpAsState(
        targetValue = if (scrollState.value > 60) (topPaddingCard / 4).dp else topPaddingCard.dp,
        label = "animatedImageSize"
    )

    Box(
        modifier = Modifier
            .wrapContentHeight()
            .background(getBackgroundColorWithGradient(userAppTheme, pokemon.dominantColor))
            .statusBarsPadding()
            .systemBarsPadding(),
    ) {
        PokemonCard(
            modifier = Modifier.padding(top = topPaddingCard.dp),
            pokemon = pokemon,
            pokemonDetails = pokemonDetails,
            scrollState = scrollState
        )
        PokemonImageAnimation(
            animatedVisibilityScope = animatedVisibilityScope,
            pokemon = pokemon,
            pokemonImageSize = animatedImageSize.times(2)
        )
        AddMemberButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(
                    top = topPaddingCard.dp - 16.dp,
                    start = 32.dp,
                ),
            isMemberYet = pokemon.isTeamMember
        ) { isAdded ->
            onAddTeamMember(pokemon, isAdded)
        }
    }
}


@Preview("Add Member Button")
@Preview("Dark Add Member Button", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AddMemberButton(
    modifier: Modifier = Modifier,
    isMemberYet: Boolean = false,
    onMemberClick: (Boolean) -> Unit = {},
) {
    Row(modifier = modifier) {
        var isTeamMember by rememberSaveable { mutableStateOf(isMemberYet) }
        val memberIconScale by animateFloatAsState(
            targetValue = if (isTeamMember) 1.1f else 1f,
            label = "Member Button Scale"
        )
        FloatingActionButton(
            modifier = Modifier
                .size(if (isTablet()) 60.dp else 40.dp)
                .aspectRatio(1f)
                .scale(memberIconScale),
            onClick = {
                isTeamMember = !isTeamMember
                onMemberClick(isTeamMember)
            },
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp),
                imageVector = if (isTeamMember) Icons.Filled.PersonRemove else Icons.Outlined.PersonAdd,
                contentDescription = "Add member icon",
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
fun PokemonCard(
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
    pokemonDetails: PokemonDetails?,
    scrollState: ScrollState,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxSize()
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            )
            .shadow(10.dp, RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        pokemonDetails?.let { pokemonDetailsNotNull ->
            Column(
                // TODO: FIX SCROLL STATE BUG
                //modifier = Modifier.verticalScroll(scrollState)
            ) {
                PokemonName(
                    modifier = Modifier.padding(
                        top = if (isTablet()) 160.dp else 120.dp,
                    ),
                    pokemon = pokemon
                )
                CustomSpacer(height = 16)
                PokemonType(
                    types = pokemonDetailsNotNull.types
                )
                PokemonMeasures(
                    pokemonWeight = pokemonDetailsNotNull.weight,
                    pokemonHeight = pokemonDetailsNotNull.height
                )
                PokemonTabRow(
                    modifier = Modifier.heightIn(
                        0.dp,
                        300.dp
                    ), // set max height due to nested scroll need to have a defined height
                    pokemonDetails = pokemonDetails
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PokemonImageAnimation(
    animatedVisibilityScope: AnimatedVisibilityScope,
    pokemon: Pokemon,
    pokemonImageSize: Dp = 200.dp,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(if (isTablet()) 4.dp else 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(URLDecoder.decode(pokemon.url, "UTF-8"))
                .crossfade(true)
                .apply { if (LocalInspectionMode.current) placeholder(R.drawable.pokeball) }
                .build(),
            contentDescription = null,
            modifier = Modifier
                .height(if (isTablet()) pokemonImageSize.plus(50.dp) else pokemonImageSize)
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
            scrollState = rememberScrollState()
        )
    }
}

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(name = "Phone Pokemon Details Screen", device = Devices.PHONE)
@Preview(name = "Tablet Pokemon Details Screen", device = Devices.TABLET)
fun PokemonDetailsScreenPreview() {
    TemplatePreviewTheme {
        PokemonDetailsScreen(
            animatedVisibilityScope = it,
            pokemon = getPokemonMock(),
            pokemonDetails = getPokemonDetailsMock(),
            onAddTeamMember = { _, _ -> },
            onBackPressed = {},
            userAppTheme = AppTheme.DARK,
        )
    }
}
