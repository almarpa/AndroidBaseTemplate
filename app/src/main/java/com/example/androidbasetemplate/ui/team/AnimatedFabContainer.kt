package com.example.androidbasetemplate.ui.team

import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.androidbasetemplate.R
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
                FabContainerState.Fab -> AddPokemonFloatingButton {
                    onFabContainerStateChanged(FabContainerState.Fullscreen)
                }

                FabContainerState.Fullscreen -> AddPokemonContent(
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
fun AddPokemonContent(onCancel: () -> Unit, onSave: (Pokemon) -> Unit) {
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
    ) {
        IconButton(
            modifier = Modifier.padding(bottom = 20.dp),
            onClick = { onCancel() },
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Outlined.Cancel,
                contentDescription = null
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = "Test 1",
                onValueChange = {},
                label = { Text(text = "Test 1") },
            )
            CustomSpacer(height = 16)
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = "Test 2",
                onValueChange = {},
                label = { Text(text = "Test 2") },
            )
            CustomSpacer(height = 16)
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = "Test 3",
                onValueChange = {},
                label = { Text(text = "Test 3") },
            )
        }
    }
}

@Composable
fun AddPokemonFloatingButton(onFabButtonPressed: () -> Unit) {
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

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview("Fab Container Button", showBackground = true)
@Preview(
    "Dark Fab Container Button",
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