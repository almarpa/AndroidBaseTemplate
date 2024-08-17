package com.example.androidtemplateapp.ui.common.utils

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.androidtemplateapp.R

@Composable
fun isTablet(): Boolean {
    val configuration = LocalConfiguration.current
    return if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        configuration.screenWidthDp > 840
    } else {
        configuration.screenWidthDp > 600
    }
}

@Composable
fun PokeballImage() {
    Image(
        modifier = Modifier.width(50.dp),
        painter = painterResource(id = R.drawable.pokeball),
        contentDescription = "PokeballImage",
    )
}