package com.alya.aiengine.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.alya.aiengine.R
import com.alya.aiengine.ui.video.FullScreenVideo

/**
 * Splash screen: plays `intro_video` fullscreen, muted, exactly once, then
 * calls [onFinished]. No buttons, no skip — matches the spec.
 */
@Composable
fun IntroScreen(onFinished: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        FullScreenVideo(
            rawRes = R.raw.intro_video,
            loop = false,
            muted = true,
            onCompleted = onFinished
        )
    }
}
