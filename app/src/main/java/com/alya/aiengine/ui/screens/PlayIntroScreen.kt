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
 * Transition screen played when the user taps PLAY GAME.
 * `play_intro_video` is the ONLY video in the app that plays with audio on.
 * It plays once, with no UI, then advances to the Game Selector.
 */
@Composable
fun PlayIntroScreen(onFinished: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        FullScreenVideo(
            rawRes = R.raw.play_intro_video,
            loop = false,
            muted = false,
            onCompleted = onFinished
        )
    }
}
