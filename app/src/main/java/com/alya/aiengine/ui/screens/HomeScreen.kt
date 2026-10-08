package com.alya.aiengine.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alya.aiengine.R
import com.alya.aiengine.ui.components.GhostButton
import com.alya.aiengine.ui.components.NeonButton
import com.alya.aiengine.ui.theme.NeonCyan
import com.alya.aiengine.ui.theme.NeonMagenta
import com.alya.aiengine.ui.theme.TextMuted
import com.alya.aiengine.ui.theme.TextSecondary
import com.alya.aiengine.ui.video.FullScreenVideo

@Composable
fun HomeScreen(
    onPlayGame: () -> Unit,
    onOpenSettings: () -> Unit = {},
    onOpenGames: () -> Unit = {},
    onOpenAbout: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {

        // Looping, muted, center-cropped background video (never stretched).
        FullScreenVideo(
            rawRes = R.raw.background_video,
            loop = true,
            muted = true
        )

        // Dark gradient overlay so UI stays legible over a bright/busy video.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xCC06070D),
                            Color(0x6606070D),
                            Color(0x8806070D),
                            Color(0xDD06070D)
                        ),
                        startY = 0f
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(horizontal = 36.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "ALYA",
                color = NeonCyan,
                fontSize = 52.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 10.sp
            )
            Text(
                text = "A I   E N G I N E",
                color = TextSecondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 6.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            NeonButton(
                text = "PLAY GAME",
                colors = listOf(NeonCyan, NeonMagenta),
                onClick = onPlayGame
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "SELECT GAME TO LAUNCH THE BOOSTED SESSION",
                color = TextMuted,
                fontSize = 11.sp,
                letterSpacing = 2.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp, Alignment.CenterHorizontally)
            ) {
                GhostButton(text = "SETTINGS", onClick = onOpenSettings)
                GhostButton(text = "GAMES", onClick = onOpenGames)
                GhostButton(text = "ABOUT", onClick = onOpenAbout)
            }

            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}
