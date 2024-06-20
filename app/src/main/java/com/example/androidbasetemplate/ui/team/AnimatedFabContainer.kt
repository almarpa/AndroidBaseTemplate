package com.example.androidbasetemplate.ui.team

import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.androidbasetemplate.R
import com.example.androidbasetemplate.common.utils.getDominantColorFromDrawable
import com.example.androidbasetemplate.entity.Pokemon
import com.example.androidbasetemplate.ui.common.preview.TemplatePreviewTheme
import com.example.androidbasetemplate.ui.common.spacer.CustomSpacer

@Composable
fun AnimatedFabContainer(
    modifier: Modifier = Modifier,
    fabContainerState: FabContainerState,
    onFabContainerStateChanged: (FabContainerState) -> Unit,
    onSave: (Pokemon) -> Unit,
) {
    with(updateTransition(targetState = fabContainerState, label = "fabContainerTransition")) {
        val backgroundColor = getBackgroundColor()
        val cornerRadius = getCornerRadius()
        AnimatedContent(
            modifier = modifier
                .shadow(0.dp, RoundedCornerShape(cornerRadius))
                .drawBehind { drawRect(backgroundColor) },
            transitionSpec = {
                fadeIn(
                    animationSpec = tween(500, easing = EaseIn)
                ).togetherWith(
                    fadeOut(
                        animationSpec = tween(200, easing = EaseOut)
                    )
                )
            },
        ) { state ->
            when (state) {
                FabContainerState.Fab -> AddPokemonFab {
                    onFabContainerStateChanged(FabContainerState.Fullscreen)
                }

                FabContainerState.Fullscreen -> AddPokemonFullscreen(
                    onCancel = { onFabContainerStateChanged(FabContainerState.Fab) },
                    onSave = { pokemon -> onSave(pokemon) }
                )
            }
        }
    }
}

@Composable
private fun Transition<FabContainerState>.getBackgroundColor(): Color {
    val backgroundColor by animateColor(label = "fabContainerColorAnim") { state ->
        when (state) {
            FabContainerState.Fab -> Color.Transparent
            FabContainerState.Fullscreen -> MaterialTheme.colorScheme.surface
        }
    }
    return backgroundColor
}

@Composable
private fun Transition<FabContainerState>.getCornerRadius(): Dp {
    val cornerRadius by animateDp(label = "fabContainerDpAnim") { state ->
        when (state) {
            FabContainerState.Fab -> 22.dp
            FabContainerState.Fullscreen -> 0.dp
        }
    }
    return cornerRadius
}

@Composable
fun AddPokemonFullscreen(onCancel: () -> Unit, onSave: (Pokemon) -> Unit) {
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
    ) {
        CustomBackButton { onCancel() }
        PokemonForm { pokemon ->
            onSave(pokemon)
        }
    }
}

@Composable
fun PokemonForm(onSave: (Pokemon) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        var pokemonImageUrl: String by remember { mutableStateOf("") }
        var pokemonName: String by remember { mutableStateOf("") }
        val primaryColor = MaterialTheme.colorScheme.primary
        var pokemonColor: Color by remember { mutableStateOf(primaryColor) }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PokemonImage(pokemonImageUrl) { pokemonColor = it }
            CustomSpacer(50)
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(.9f),
                value = pokemonImageUrl,
                onValueChange = { pokemonImageUrl = it },
                placeholder = { Text(text = stringResource(R.string.add_photo)) },
                label = { Text(text = stringResource(R.string.pokemon_photo)) },
            )
            CustomSpacer(height = 30)
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(.9f),
                value = pokemonName,
                onValueChange = { pokemonName = it },
                placeholder = { Text(text = stringResource(R.string.insert_name)) },
                label = { Text(text = stringResource(R.string.pokemon_name)) },
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth(.7f)
                .padding(bottom = 20.dp),
            enabled = checkFields(pokemonName, pokemonImageUrl),
            onClick = { /* TODO : onSave(Pokemon(url = pokemonImageUrl, name = pokemonName)) */ },
        ) {
            Text(
                modifier = Modifier.padding(vertical = 6.dp),
                text = stringResource(R.string.common_save)
            )
        }
    }
}

@Composable
fun PokemonImage(pokemonImage: String, dominantColor: (Color) -> Unit) {
    var cardDominantColor by remember { mutableStateOf(Color.Transparent) }
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .padding(20.dp),
        shape = AbsoluteCutCornerShape(40.dp),
        colors = CardDefaults.cardColors(containerColor = cardDominantColor)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemonImage)
                    .crossfade(true)
                    .placeholder(R.drawable.add_a_photo)
                    .build(),
                contentDescription = "Member Image",
                contentScale = ContentScale.FillBounds,
                loading = { CircularProgressIndicator() },
                onSuccess = { success ->
                    getDominantColorFromDrawable(success.result.drawable) {
                        cardDominantColor = it
                        dominantColor(it)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(.9f)
                    .aspectRatio(1f),
            )
        }
    }
}

fun checkFields(pokemonName: String, pokemonImageUrl: String) =
    pokemonName.isNotEmpty() && pokemonImageUrl.isNotEmpty()

@Composable
fun CustomBackButton(onCancel: () -> Unit) {
    IconButton(onClick = { onCancel() }) {
        Icon(
            modifier = Modifier.fillMaxSize(),
            imageVector = Icons.AutoMirrored.Default.ArrowBack,
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = null
        )
    }
}

@Composable
fun AddPokemonFab(onFabButtonPressed: () -> Unit) {
    Button(
        modifier = Modifier.padding(end = 16.dp, bottom = 20.dp),
        colors = ButtonDefaults.buttonColors(),
        onClick = { onFabButtonPressed() },
    ) {
        Box {
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .offset(x = (-16).dp, y = (-12).dp)
                    .zIndex(1f),
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(R.string.menu_drawer_btn),
                tint = Color.White
            )
            Image(
                modifier = Modifier.width(50.dp),
                painter = painterResource(id = R.drawable.pokeball),
                contentDescription = "Splash",
            )
        }
    }
}

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview("Animated Fab Container", showBackground = true)
@Preview(
    "Dark Animated Fab Container",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
fun AddPokemonFloatingButtonPreview() {
    TemplatePreviewTheme {
        AnimatedFabContainer(
            fabContainerState = FabContainerState.Fab,
            onFabContainerStateChanged = {},
            onSave = {}
        )
    }
}

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview("Fab Container Fullscreen", showBackground = true)
@Preview(
    "Dark Fab Container Fullscreen",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
fun FabContainerFullscreenPreview() {
    TemplatePreviewTheme {
        AnimatedFabContainer(
            fabContainerState = FabContainerState.Fullscreen,
            onFabContainerStateChanged = {},
            onSave = {}
        )
    }
}