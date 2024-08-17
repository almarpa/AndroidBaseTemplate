package com.example.androidtemplateapp.ui.team

import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.androidtemplateapp.R
import com.example.androidtemplateapp.common.utils.getDominantColorFromDrawable
import com.example.androidtemplateapp.entity.Pokemon
import com.example.androidtemplateapp.ui.common.preview.TemplatePreviewTheme
import com.example.androidtemplateapp.ui.common.spacer.CustomSpacer
import com.example.androidtemplateapp.ui.common.utils.PokeballImage
import com.example.androidtemplateapp.ui.common.utils.isTablet
import kotlinx.coroutines.delay
import java.net.URLDecoder

@Composable
fun AnimatedFabContainer(
    modifier: Modifier = Modifier,
    fabContainerState: Boolean,
    onFabContainerStateChanged: (Boolean) -> Unit,
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
            if (state) {
                AddPokemonFullscreen(
                    onCancel = { onFabContainerStateChanged(false) },
                    onSave = { pokemon -> onSave(pokemon) }
                )
            } else {
                AddPokemonFab {
                    onFabContainerStateChanged(true)
                }
            }
        }
    }
}

@Composable
private fun Transition<Boolean>.getBackgroundColor(): Color {
    val backgroundColor by animateColor(label = "fabContainerColorAnim") { state ->
        if (state) {
            MaterialTheme.colorScheme.surface
        } else {
            Color.Transparent
        }
    }
    return backgroundColor
}

@Composable
private fun Transition<Boolean>.getCornerRadius(): Dp {
    val cornerRadius by animateDp(label = "fabContainerDpAnim") { state ->
        if (state) {
            0.dp
        } else {
            22.dp
        }
    }
    return cornerRadius
}

@Composable
fun AddPokemonFullscreen(onCancel: () -> Unit, onSave: (Pokemon) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        CustomBackButton { onCancel() }
        PokemonForm { pokemon -> onSave(pokemon) }
    }
}

@Composable
fun PokemonForm(onSave: (Pokemon) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        var pokemonImageUrl: String by rememberSaveable { mutableStateOf("") }
        var pokemonImageUrlToLoad: String by rememberSaveable { mutableStateOf("") }
        var pokemonName: String by rememberSaveable { mutableStateOf("") }
        var pokemonColor: Color by remember { mutableStateOf(Color.Transparent) }
        var validImageURL: Boolean by rememberSaveable { mutableStateOf(false) }

        LaunchedEffect(pokemonImageUrl) {
            delay(1000L)
            pokemonImageUrlToLoad = pokemonImageUrl
        }

        PokemonImageCard(
            pokemonImageURL = pokemonImageUrlToLoad,
            onError = {
                pokemonColor = Color.Transparent
                validImageURL = false
            },
            onSuccess = {
                pokemonColor = it
                validImageURL = true
            }
        )
        CustomSpacer(modifier = Modifier.weight(1f))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(.9f),
            value = pokemonImageUrl,
            onValueChange = { pokemonImageUrl = it },
            placeholder = { Text(text = stringResource(R.string.add_photo)) },
            label = { Text(text = stringResource(R.string.pokemon_photo)) },
            maxLines = if (isTablet()) 1 else 3,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            isError = !validImageURL && pokemonImageUrl.isNotEmpty(),
        )
        CustomSpacer(height = 20)
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(.9f),
            value = pokemonName,
            onValueChange = { pokemonName = it },
            placeholder = { Text(text = stringResource(R.string.insert_name)) },
            label = { Text(text = stringResource(R.string.pokemon_name)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            maxLines = if (isTablet()) 1 else 2,
        )
        CustomSpacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier
                .fillMaxWidth(.6f)
                .padding(vertical = 20.dp),
            enabled = checkFields(pokemonName, pokemonImageUrl),
            onClick = {
                onSave(
                    Pokemon(
                        url = pokemonImageUrl,
                        name = pokemonName,
                        dominantColor = pokemonColor.toArgb(),
                    )
                )
            },
        ) {
            Text(
                modifier = Modifier.padding(vertical = 6.dp),
                text = stringResource(R.string.common_save)
            )
        }
        CustomSpacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun PokemonImageCard(
    pokemonImageURL: String,
    onError: () -> Unit,
    onSuccess: (Color) -> Unit,
) {
    var cardDominantColor: Color by remember { mutableStateOf(Color.Transparent) }

    Card(
        modifier = Modifier
            .padding(horizontal = 20.dp),
        shape = AbsoluteCutCornerShape(40.dp),
        colors = CardDefaults.cardColors(containerColor = cardDominantColor)
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxWidth(if (isTablet()) .25f else .8f)
                .aspectRatio(1f),
            model = ImageRequest.Builder(LocalContext.current)
                .data(URLDecoder.decode(pokemonImageURL, "UTF-8"))
                .placeholder(R.drawable.pokeball)
                .crossfade(true)
                .build(),
            contentDescription = "Member Image",
            contentScale = ContentScale.FillBounds,
            error = { PokeballImage() },
            onError = {
                onError()
                cardDominantColor = Color.Transparent
            },
            onSuccess = { success ->
                getDominantColorFromDrawable(success.result.drawable) {
                    cardDominantColor = it
                    onSuccess(it)
                }
            }
        )
    }

}

fun checkFields(pokemonName: String, pokemonImageUrl: String?) =
    !pokemonImageUrl.isNullOrEmpty() && pokemonName.isNotEmpty()

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
        modifier = Modifier.padding(end = 16.dp, bottom = 16.dp),
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
            PokeballImage()
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
            fabContainerState = false,
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
@Preview(
    "Fab Container Fullscreen Landscape",
    showBackground = true,
    device = "spec:width=400dp,height=900dp,dpi=420,orientation=landscape"
)
@Preview(name = "Tablet Fab Container Fullscreen", device = Devices.TABLET, showBackground = true)
@Preview(
    "Dark Tablet Fab Container Fullscreen",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.TABLET
)
fun FabContainerFullscreenPreview() {
    TemplatePreviewTheme {
        AnimatedFabContainer(
            fabContainerState = true,
            onFabContainerStateChanged = {},
            onSave = {}
        )
    }
}