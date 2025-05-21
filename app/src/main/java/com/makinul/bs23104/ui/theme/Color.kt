package com.makinul.bs23104.ui.theme

import androidx.compose.ui.graphics.Color

// Existing colors from colors.xml
val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)

val Purple200 = Color(0xFFBB86FC) // Existing: purple_200
val Purple500 = Color(0xFF6200EE) // Existing: purple_500
val Purple700 = Color(0xFF3700B3) // Existing: purple_700
val Teal200 = Color(0xFF03DAC5)   // Existing: teal_200
val Teal700 = Color(0xFF018786)   // Existing: teal_700

val TextPrimary = Color(0xFF212121) // Existing: text_primary
val TextSecondary = Color(0xFF757575) // Existing: text_secondary
val PriceColor = Color(0xFFFF5722)   // Existing: price_color
val DiscountTextColor = Color(0xFFFFFFFF) // Existing: discount_text_color
val DiscountBackgroundColor = Color(0xFF4CAF50) // Existing: discount_background_color
val AvailabilityInStock = Color(0xFF4CAF50) // Existing: availability_in_stock
val RatingColor = Color(0xFFFFC107) // Existing: rating_color

val HeaderFooterBackground = Color(0xFFF0F0F0) // Existing: header_footer_background
val HeaderFooterText = Color(0xFF333333)       // Existing: header_footer_text
val Gray = Color(0xFFC2C2C2)                 // Existing: gray

// Material 3 Color Palette
// Primary colors - Using existing purple shades
val Purple40 = Purple500 // Primary for Light Theme (Derived from purple_500)
val Purple80 = Purple200 // Primary for Dark Theme (Derived from purple_200)

// Secondary colors - Using existing teal shades
val PurpleGrey40 = Teal700 // Secondary for Light Theme (Derived from teal_700)
val PurpleGrey80 = Teal200 // Secondary for Dark Theme (Derived from teal_200)

// Tertiary colors - Let's use a pinkish tone, common in Material 3 examples
val Pink40 = Color(0xFF7D5260) // Tertiary for Light Theme (M3 Baseline)
val Pink80 = Color(0xFFEFB8C8) // Tertiary for Dark Theme (M3 Baseline)

// Neutral colors
val Grey10 = Color(0xFF1C1B1F) // M3 Baseline Dark Background/OnLight
val Grey20 = Color(0xFF313033)
val Grey80 = Color(0xFFC8C5CA)
val Grey90 = Color(0xFFE6E1E5) // M3 Baseline Light Background/OnDark

// Functional colors
val Error40 = Color(0xFFB3261E) // M3 Baseline Error for Light Theme
val Error80 = Color(0xFFF2B8B5) // M3 Baseline Error for Dark Theme

// Background and Surface colors
val LightBackground = Color(0xFFFFFBFE) // M3 Baseline
val DarkBackground = Color(0xFF1C1B1F)  // M3 Baseline

val LightSurface = Color(0xFFFFFBFE)    // M3 Baseline
val DarkSurface = Color(0xFF1C1B1F)     // M3 Baseline

// On-colors (Text/icon colors on top of primary, secondary, etc.)
val OnPrimaryLight = White
val OnPrimaryDark = Color(0xFF381E72) // M3 Baseline for Purple80

val OnSecondaryLight = White
val OnSecondaryDark = Color(0xFF1D352D) // M3 Baseline for Teal80 (PurpleGrey80)

val OnTertiaryLight = White
val OnTertiaryDark = Color(0xFF492532)  // M3 Baseline for Pink80

val OnBackgroundLight = Grey10
val OnBackgroundDark = Grey90

val OnSurfaceLight = Grey10
val OnSurfaceDark = Grey90

val OnErrorLight = White
val OnErrorDark = Color(0xFF601410) // M3 Baseline for Error80

// Surface Variants & Outlines (Important for M3 components like Cards, Buttons)
val LightSurfaceVariant = Color(0xFFE7E0EC) // M3 Baseline
val DarkSurfaceVariant = Color(0xFF49454F)  // M3 Baseline

val OutlineLight = Color(0xFF79747E)      // M3 Baseline
val OutlineDark = Color(0xFF938F99)       // M3 Baseline
