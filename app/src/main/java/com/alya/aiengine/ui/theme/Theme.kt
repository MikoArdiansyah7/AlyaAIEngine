package com.alya.aiengine.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val AlyaColorScheme = darkColorScheme(
    primary = NeonCyan,
    secondary = NeonMagenta,
    tertiary = NeonViolet,
    background = AlyaBg,
    surface = AlyaBgSoft,
    onPrimary = AlyaBg,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

@Composable
fun AlyaAIEngineTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AlyaColorScheme,
        typography = AlyaTypography,
        content = content
    )
}
