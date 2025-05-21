package com.makinul.bs23104.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Updated Light Color Scheme using colors from Color.kt
private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    onPrimary = OnPrimaryLight,
    primaryContainer = PurpleGrey40, // Example: using secondary as container for now
    onPrimaryContainer = OnSecondaryLight, // Example
    secondary = Teal200, // Using Teal200 as secondary
    onSecondary = Black, // Text on Teal200
    secondaryContainer = Teal700, // Darker Teal for container
    onSecondaryContainer = White,
    tertiary = Pink40,
    onTertiary = OnTertiaryLight,
    tertiaryContainer = Pink80, // Lighter Pink for container
    onTertiaryContainer = Black, // Text on Pink80
    error = Error40,
    onError = OnErrorLight,
    errorContainer = Error80, // Lighter error color for container
    onErrorContainer = OnErrorDark, // Text on Error80
    background = LightBackground,
    onBackground = OnBackgroundLight,
    surface = LightSurface,
    onSurface = OnSurfaceLight,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = OnBackgroundLight, // Typically a darker shade on light variant
    outline = OutlineLight,
    inverseOnSurface = OnBackgroundDark, // For snackbars, etc.
    inverseSurface = DarkBackground,  // For snackbars, etc.
    inversePrimary = Purple80, // For specific cases needing inverted primary
    surfaceTint = Purple40 // Usually same as primary
)

// Updated Dark Color Scheme using colors from Color.kt
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    onPrimary = OnPrimaryDark,
    primaryContainer = PurpleGrey80, // Example
    onPrimaryContainer = OnSecondaryDark, // Example
    secondary = Teal200, // Teal200 also for dark theme secondary
    onSecondary = Black, // Text on Teal200
    secondaryContainer = Teal700,
    onSecondaryContainer = White,
    tertiary = Pink80,
    onTertiary = OnTertiaryDark,
    tertiaryContainer = Pink40, // Darker Pink for container
    onTertiaryContainer = White, // Text on Pink40
    error = Error80,
    onError = OnErrorDark,
    errorContainer = Error40, // Darker error color for container
    onErrorContainer = OnErrorLight, // Text on Error40
    background = DarkBackground,
    onBackground = OnBackgroundDark,
    surface = DarkSurface,
    onSurface = OnSurfaceDark,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = OnBackgroundDark, // Typically a lighter shade on dark variant
    outline = OutlineDark,
    inverseOnSurface = OnBackgroundLight, // For snackbars, etc.
    inverseSurface = LightBackground, // For snackbars, etc.
    inversePrimary = Purple40, // For specific cases needing inverted primary
    surfaceTint = Purple80 // Usually same as primary
)

@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    useDynamicColors: Boolean = true, // Material You enabled by default
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val colorScheme = when {
        useDynamicColors && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (useDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        useDarkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography, // From Type.kt
        content = content
    )
}
