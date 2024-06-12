package com.example.androidbasetemplate.ui.team

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidbasetemplate.ui.common.preview.TemplatePreviewTheme

@Composable
fun FabContainer(
    modifier: Modifier = Modifier,
    fabContainerState: FabContainerState,
    onFabContainerStateChanged: (FabContainerState) -> Unit,
) {
    val transition = updateTransition(targetState = fabContainerState, label = "fabTransition")
    val backgroundColor by transition.animateColor(label = "fabContainerColorAnim") { state ->
        when (state) {
            FabContainerState.Fab -> Color.Transparent
            FabContainerState.Fullscreen -> MaterialTheme.colorScheme.primary
        }
    }
    val cornerRadius by transition.animateDp(label = "fabContainerDpAnim") { state ->
        when (state) {
            FabContainerState.Fab -> 22.dp
            FabContainerState.Fullscreen -> 0.dp
        }
    }
    transition.AnimatedContent(
        modifier = modifier
            .shadow(
                elevation = 0.dp,
                shape = RoundedCornerShape(cornerRadius)
            )
            .drawBehind { drawRect(backgroundColor) }
    ) { state ->
        when (state) {
            FabContainerState.Fab -> AddPokemonFloatingButton {
                onFabContainerStateChanged(FabContainerState.Fullscreen)
            }

            FabContainerState.Fullscreen -> AddPokemonContent {
                onFabContainerStateChanged(FabContainerState.Fab)
            }
        }
    }
}

@Composable
fun AddPokemonFloatingButton(onFabButtonPressed: () -> Unit) {
    ExtendedFloatingActionButton(
        modifier = Modifier.padding(16.dp),
        onClick = { onFabButtonPressed() },
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(
            imageVector = Icons.Default.Add, contentDescription = null,
        )
    }
}

@Composable
fun AddPokemonContent(onCancel: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxSize(),
    ) {
        IconButton(onClick = { onCancel() }) {
            Icon(imageVector = Icons.Default.Cancel, contentDescription = null)
        }
        TextField(
            value = "Test",
            onValueChange = {},
            label = { Text(text = "Test") },
        )
    }
}

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview("Fab Container Button", showBackground = true)
fun AddPokemonFloatingButtonPreview() {
    TemplatePreviewTheme {
        FabContainer(fabContainerState = FabContainerState.Fab) {}
    }
}

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview("Fab Container Fullscreen", showBackground = true)
fun FabContainerFullscreenPreview() {
    TemplatePreviewTheme {
        FabContainer(fabContainerState = FabContainerState.Fullscreen) {}
    }
}