package com.example.androidtemplateapp.ui.pokemondetails

import android.annotation.SuppressLint
import android.content.res.Configuration
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
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Devices
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
import com.example.androidtemplateapp.ui.common.spacer.CustomSpacer
import com.example.androidtemplateapp.ui.common.topappbar.DefaultTopAppBar
import com.example.androidtemplateapp.ui.common.utils.isTablet
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
    BackHandler { onBackPressed() }
    Scaffold(
        topBar = { DefaultTopAppBar(title = R.string.empty_string) { onBackPressed() } },
    ) {
        PokemonDetailsContent(
            userAppTheme = userAppTheme,
            pokemon = pokemon,
            pokemonDetails = pokemonDetails,
            animatedVisibilityScope = animatedVisibilityScope,
            onAddTeamMember = onAddTeamMember
        )
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(getBackgroundColor(userAppTheme, pokemon.dominantColor))
            .statusBarsPadding()
            .systemBarsPadding(),
    ) {
        PokemonCard(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 150.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .shadow(10.dp, RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 16.dp),
            pokemon = pokemon,
            pokemonDetails = pokemonDetails,
        )
        PokemonImageAnimation(animatedVisibilityScope, pokemon)
        AddMemberButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(
                    top = 166.dp,
                    start = 32.dp,
                ),
            isMemberYet = pokemon.isTeamMember
        ) { isAdded ->
            onAddTeamMember(
                pokemon,
                isAdded
            )
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
    Box(modifier = modifier) {
        var isTeamMember by remember { mutableStateOf(isMemberYet) }
        val memberIconScale by animateFloatAsState(
            targetValue = if (isTeamMember) 1.2f else 1f,
            label = "Member Button Scale"
        )
        IconButton(
            modifier = Modifier
                .size(if (isTablet()) 70.dp else 40.dp)
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
) {
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .wrapContentHeight()
            .padding(
                top = if (isTablet()) 230.dp else 20.dp,
            )
    ) {
        pokemonDetails?.let { pokemonDetailsNotNull ->
            PokemonName(pokemon)
            CustomSpacer(height = 16)
            Column(modifier = Modifier.verticalScroll(scrollState)) {
                PokemonType(pokemonDetailsNotNull.types)
                CustomSpacer(height = 8)
                PokemonMeasures(pokemonDetailsNotNull.weight, pokemonDetailsNotNull.height)
                CustomSpacer(height = 8)
                PokemonTabRow(Modifier.height(300.dp), pokemonDetails)
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PokemonImageAnimation(
    animatedVisibilityScope: AnimatedVisibilityScope,
    pokemon: Pokemon,
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
                .fillMaxWidth(if (isTablet()) 0.28f else 0.6f)
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
            userAppTheme = AppTheme.DARK
        )
    }
}
