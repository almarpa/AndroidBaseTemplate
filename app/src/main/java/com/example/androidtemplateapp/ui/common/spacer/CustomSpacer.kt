package com.example.androidtemplateapp.ui.common.spacer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomSpacer(height: Int = 0, width: Int = 0) {
    Spacer(
        modifier = Modifier
            .width(width.dp)
            .height(height.dp)
    )
}
