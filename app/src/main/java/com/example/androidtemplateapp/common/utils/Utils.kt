package com.example.androidtemplateapp.common.utils

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.core.os.LocaleListCompat
import androidx.palette.graphics.Palette
import com.example.androidtemplateapp.entity.enums.AppTheme

fun getDominantColorFromDrawable(drawable: Drawable, onFinish: (Color) -> Unit) {
    (drawable as? BitmapDrawable)?.bitmap?.copy(Bitmap.Config.ARGB_8888, true)?.let { bitmap ->
        Palette.from(bitmap).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }
}

fun setAppLanguage(locale: String) {
    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(locale))
}

@Composable
fun getBackgroundColorWithGradient(userAppTheme: AppTheme, dominantColor: Int): Brush {
    val color = Color(dominantColor)
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

private fun getLightGradientByColor(dominantColor: Color) =
    Brush.verticalGradient(
        listOf(Color.White, dominantColor),
        startY = 0.0f,
        endY = 400.0f
    )

private fun getDarkGradientByColor(dominantColor: Color) =
    Brush.verticalGradient(
        listOf(Color.Black, dominantColor),
        startY = 0.0f,
        endY = 400.0f
    )