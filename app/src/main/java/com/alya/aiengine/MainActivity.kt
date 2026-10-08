package com.alya.aiengine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.alya.aiengine.ui.Screen
import com.alya.aiengine.ui.screens.GameSelectorScreen
import com.alya.aiengine.ui.screens.HomeScreen
import com.alya.aiengine.ui.screens.IntroScreen
import com.alya.aiengine.ui.screens.PlayIntroScreen
import com.alya.aiengine.ui.theme.AlyaAIEngineTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        hideSystemBars()

        setContent {
            AlyaAIEngineTheme {
                var screen by remember { mutableStateOf(Screen.INTRO) }

                Crossfade(
                    targetState = screen,
                    animationSpec = tween(durationMillis = 420),
                    modifier = Modifier.fillMaxSize(),
                    label = "alya-screen-crossfade"
                ) { current ->
                    when (current) {
                        Screen.INTRO -> IntroScreen(
                            onFinished = { screen = Screen.HOME }
                        )

                        Screen.HOME -> HomeScreen(
                            onPlayGame = { screen = Screen.PLAY_INTRO }
                        )

                        Screen.PLAY_INTRO -> PlayIntroScreen(
                            onFinished = { screen = Screen.GAME_SELECTOR }
                        )

                        Screen.GAME_SELECTOR -> GameSelectorScreen(
                            onBack = { screen = Screen.HOME }
                        )
                    }
                }
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemBars()
    }

    /** Edge-to-edge, immersive: hide status + navigation bars so video/UI use the full screen. */
    private fun hideSystemBars() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}
