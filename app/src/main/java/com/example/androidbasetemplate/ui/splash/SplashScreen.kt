package com.example.androidbasetemplate.ui.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.androidbasetemplate.R
import com.example.androidbasetemplate.ui.common.NavigationActions
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(navigationActions: NavigationActions) {
    val rotationState = remember { Animatable(0f) }
    val splashAnimationFinished = remember { mutableStateOf(false) }
    val animDuration = 2000

    LaunchedEffect(key1 = true) {
        rotationState.animateTo(
            targetValue = 720f,
            animationSpec = tween(durationMillis = animDuration),
        ) {
            launch {
                delay(animDuration.toLong())
                splashAnimationFinished.value = true
            }
        }
    }

    if (!splashAnimationFinished.value) {
        SplashContent(rotationState)
    } else {
        navigationActions.navigateToPokemonList()
    }
}

@Composable
fun SplashContent(rotationState: Animatable<Float, AnimationVector1D>) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer(rotationZ = rotationState.value),
    ) {
        Image(
            modifier = Modifier.width(50.dp),
            painter = painterResource(id = R.drawable.pokeball),
            contentDescription = "Splash",
        )
    }
}
