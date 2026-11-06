package com.m57.hermescontrol.theme.presets

import androidx.compose.ui.graphics.Color
import com.m57.hermescontrol.theme.HermesStatusColors
import com.m57.hermescontrol.theme.PaletteColors
import com.m57.hermescontrol.theme.buildTheme

// ---------------------------------------------------------------------
// Monochrome Dark — every swatch is R=G=B; roles are distinguished
// purely by lightness, not hue.
// ---------------------------------------------------------------------

private val MonoDarkFloor = Color(0xFF0A0A0A) // surfaceContainerLowest
private val MonoDarkBg = Color(0xFF121212) // background, surface, containerLow, all "on*" ink
private val MonoDarkContainer = Color(0xFF1E1E1E) // surfaceContainer
private val MonoDarkContainerHigh = Color(0xFF2A2A2A) // surfaceContainerHigh, surfaceVariant, status containers
private val MonoDarkContainerHighest = Color(0xFF363636) // surfaceContainerHighest, primaryContainer
private val MonoDarkOutline = Color(0xFF808080)
private val MonoDarkOutlineVariant = Color(0xFF404040)
private val MonoDarkTextDim = Color(0xFFB3B3B3) // onSurfaceVariant
private val MonoDarkTextBright = Color(0xFFF2F2F2) // onBackground/onSurface/inverseSurface/primary
private val MonoDarkSecondary = Color(0xFFCCCCCC) // secondary accent
private val MonoDarkTertiary = Color(0xFF999999) // tertiary accent

// Batch-10 G5: hued semantic status colors — desaturated (~28% saturation,
// ~56% lightness, one shared tone family) so they sit inside the grayscale
// ramp yet read unmistakably as green/amber/red/blue. Surfaces stay mono;
// only status gains hue (policy comment at the theme val).
private val MonoDarkStatusGreen = Color(0xFF6FAF7E) // success
private val MonoDarkStatusAmber = Color(0xFFAF9F6F) // warning
private val MonoDarkStatusRed = Color(0xFFAF716F) // error
private val MonoDarkStatusBlue = Color(0xFF6F8FAF) // info

// ---------------------------------------------------------------------
// Monochrome Light — the same ramp, inverted
// ---------------------------------------------------------------------

private val MonoLightCeiling = Color(0xFFFFFFFF) // surfaceContainerLowest
private val MonoLightBg = Color(0xFFFAFAFA) // background, surface, containerLow, all "on*" ink
private val MonoLightContainer = Color(0xFFF0F0F0) // surfaceContainer
private val MonoLightContainerHigh = Color(0xFFE0E0E0) // surfaceContainerHigh, surfaceVariant, status containers
private val MonoLightContainerHighest = Color(0xFFD1D1D1) // surfaceContainerHighest, primaryContainer
private val MonoLightOutlineVariant = Color(0xFFCCCCCC)
private val MonoLightTextDim = Color(0xFF4D4D4D) // onSurfaceVariant
private val MonoLightTextBright = Color(0xFF121212) // onBackground/onSurface/inverseSurface/primary
private val MonoLightSecondary = Color(0xFF333333) // secondary accent
private val MonoLightTertiary = Color(0xFF666666) // tertiary accent

// Batch-10 G5: light-mode status — the same hue family as the dark set,
// darkened for contrast on the near-white ramp.
private val MonoLightStatusGreen = Color(0xFF4A7D5A) // success
private val MonoLightStatusAmber = Color(0xFF7D6B3F) // warning
private val MonoLightStatusRed = Color(0xFF8A4A48) // error
private val MonoLightStatusBlue = Color(0xFF3F5D7D) // info

/**
 * Monochrome theme — surfaces and ink are pure grayscale (roles are
 * distinguished by lightness, not hue), but the semantic status colors
 * (batch-10 G5) are HUED and desaturated: success green, warning amber,
 * error red, info blue. Status icons/dots/switches read by hue in the
 * Black/White presets while everything else keeps the mono discipline;
 * the set stays desaturated, so pair critical status with an icon or
 * label as usual.
 */
val MonochromeTheme =
    buildTheme(
        dark =
            PaletteColors(
                primary = MonoDarkTextBright,
                onPrimary = MonoDarkBg,
                primaryContainer = MonoDarkContainerHighest,
                onPrimaryContainer = MonoDarkTextBright,
                secondary = MonoDarkSecondary,
                onSecondary = MonoDarkBg,
                secondaryContainer = MonoDarkContainerHigh,
                onSecondaryContainer = MonoDarkSecondary,
                tertiary = MonoDarkTertiary,
                onTertiary = MonoDarkBg,
                tertiaryContainer = MonoDarkContainerHigh,
                onTertiaryContainer = MonoDarkTertiary,
                background = MonoDarkBg,
                onBackground = MonoDarkTextBright,
                surface = MonoDarkBg,
                onSurface = MonoDarkTextBright,
                surfaceVariant = MonoDarkContainerHigh,
                onSurfaceVariant = MonoDarkTextDim,
                surfaceDim = MonoDarkBg,
                surfaceBright = MonoDarkContainerHighest,
                primaryFixed = MonoDarkTextBright,
                primaryFixedDim = MonoDarkTextBright,
                onPrimaryFixed = MonoDarkBg,
                onPrimaryFixedVariant = MonoDarkBg,
                secondaryFixed = MonoDarkSecondary,
                secondaryFixedDim = MonoDarkSecondary,
                onSecondaryFixed = MonoDarkBg,
                onSecondaryFixedVariant = MonoDarkBg,
                tertiaryFixed = MonoDarkTertiary,
                tertiaryFixedDim = MonoDarkTertiary,
                onTertiaryFixed = MonoDarkBg,
                onTertiaryFixedVariant = MonoDarkBg,
                surfaceContainerLowest = MonoDarkFloor,
                surfaceContainerLow = MonoDarkBg,
                surfaceContainer = MonoDarkContainer,
                surfaceContainerHigh = MonoDarkContainerHigh,
                surfaceContainerHighest = MonoDarkContainerHighest,
                inverseSurface = MonoDarkTextBright,
                inverseOnSurface = MonoDarkBg,
                inversePrimary = MonoLightTextBright,
                outline = MonoDarkOutline,
                outlineVariant = MonoDarkOutlineVariant,
                scrim = Color.Black,
                status =
                    HermesStatusColors(
                        success = MonoDarkStatusGreen,
                        successContainer = MonoDarkContainerHigh,
                        onSuccess = MonoDarkBg,
                        warning = MonoDarkStatusAmber,
                        warningContainer = MonoDarkContainerHigh,
                        onWarning = MonoDarkBg,
                        error = MonoDarkStatusRed,
                        errorContainer = MonoDarkContainerHigh,
                        onError = MonoDarkBg,
                        onErrorContainer = MonoDarkTextBright,
                        info = MonoDarkStatusBlue,
                        infoContainer = MonoDarkContainerHigh,
                        onInfo = MonoDarkBg,
                    ),
            ),
        light =
            PaletteColors(
                primary = MonoLightTextBright,
                onPrimary = MonoLightBg,
                primaryContainer = MonoLightContainerHighest,
                onPrimaryContainer = MonoLightTextBright,
                secondary = MonoLightSecondary,
                onSecondary = MonoLightBg,
                secondaryContainer = MonoLightContainerHigh,
                onSecondaryContainer = MonoLightSecondary,
                tertiary = MonoLightTertiary,
                onTertiary = MonoLightBg,
                tertiaryContainer = MonoLightContainerHigh,
                onTertiaryContainer = MonoLightSecondary,
                background = MonoLightBg,
                onBackground = MonoLightTextBright,
                surface = MonoLightBg,
                onSurface = MonoLightTextBright,
                surfaceVariant = MonoLightContainerHigh,
                onSurfaceVariant = MonoLightTextDim,
                surfaceDim = MonoLightContainerHighest,
                surfaceBright = MonoLightBg,
                primaryFixed = MonoDarkTextBright,
                primaryFixedDim = MonoDarkTextBright,
                onPrimaryFixed = MonoDarkBg,
                onPrimaryFixedVariant = MonoDarkBg,
                secondaryFixed = MonoDarkSecondary,
                secondaryFixedDim = MonoDarkSecondary,
                onSecondaryFixed = MonoDarkBg,
                onSecondaryFixedVariant = MonoDarkBg,
                tertiaryFixed = MonoDarkTertiary,
                tertiaryFixedDim = MonoDarkTertiary,
                onTertiaryFixed = MonoDarkBg,
                onTertiaryFixedVariant = MonoDarkBg,
                surfaceContainerLowest = MonoLightCeiling,
                surfaceContainerLow = MonoLightBg,
                surfaceContainer = MonoLightContainer,
                surfaceContainerHigh = MonoLightContainerHigh,
                surfaceContainerHighest = MonoLightContainerHighest,
                inverseSurface = MonoLightTextBright,
                inverseOnSurface = MonoLightBg,
                inversePrimary = MonoDarkTextBright,
                outline = MonoLightTertiary,
                outlineVariant = MonoLightOutlineVariant,
                scrim = Color.Black,
                status =
                    HermesStatusColors(
                        success = MonoLightStatusGreen,
                        successContainer = MonoLightContainerHigh,
                        onSuccess = MonoLightBg,
                        warning = MonoLightStatusAmber,
                        warningContainer = MonoLightContainerHigh,
                        onWarning = MonoLightBg,
                        error = MonoLightStatusRed,
                        errorContainer = MonoLightContainerHigh,
                        onError = MonoLightBg,
                        onErrorContainer = MonoLightTextBright,
                        info = MonoLightStatusBlue,
                        infoContainer = MonoLightContainerHigh,
                        onInfo = MonoLightBg,
                    ),
            ),
    )
