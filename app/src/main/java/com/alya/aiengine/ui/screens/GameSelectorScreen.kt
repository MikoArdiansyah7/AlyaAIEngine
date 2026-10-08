package com.alya.aiengine.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.NetworkCell
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alya.aiengine.data.Game
import com.alya.aiengine.data.GameRepository

// Light "chrome" palette, deliberately separate from the rest of the app's
// dark neon theme — this screen mirrors the reference booster HUD look.
private val HudBgTop = Color(0xFFEDEFF3)
private val HudBgBottom = Color(0xFFC6CAD3)
private val HudCard = Color(0xE6F4F5F8)
private val HudCardBorder = Color(0x3322E6FF)
private val HudTextDark = Color(0xFF14161C)
private val HudTextMuted = Color(0xFF6B707C)
private val HudChip = Color(0xB3FFFFFF)
private val FpsGreen = Color(0xFF32E37A)

/**
 * The booster "home HUD" shown after `play_intro_video`: one game at a time,
 * styled like a real game-booster overlay — status chips, a selected-mode
 * badge, and a big START button. Tap the "+" to switch games.
 *
 * Note: the FPS/network/battery values shown here are static placeholder
 * labels, not live readings — real monitoring is stage 2.
 */
@Composable
fun GameSelectorScreen(onBack: () -> Unit) {
    var currentGame by remember { mutableStateOf(GameRepository.dummyGames.first()) }
    var showPicker by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(HudBgTop, HudBgBottom)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(horizontal = 28.dp, vertical = 16.dp)
        ) {
            TopStatusBar(
                onBack = onBack,
                onAddGame = { showPicker = true }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = currentGame.boostMode,
                color = FpsGreen,
                fontSize = 40.sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text = "SELECTED MODE",
                color = HudTextMuted,
                fontSize = 10.sp,
                letterSpacing = 2.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                BoosterCard(
                    game = currentGame,
                    onStart = { /* stage 2: launch real boost session */ }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "\u26A1", fontSize = 14.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "ALYA AI ENGINE",
                    color = HudTextMuted,
                    fontSize = 12.sp,
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        AnimatedVisibility(
            visible = showPicker,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            GamePickerOverlay(
                selectedId = currentGame.id,
                onPick = { currentGame = it; showPicker = false },
                onDismiss = { showPicker = false }
            )
        }
    }
}

@Composable
private fun TopStatusBar(onBack: () -> Unit, onAddGame: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Hex-style icon button on the left doubles as "back to home".
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(HudChip)
                .clickable(onClick = onBack),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.Settings, contentDescription = "Back", tint = HudTextDark)
        }

        StatusChip(icon = Icons.Filled.NetworkCell, label = "Network", value = "Strong")

        // Center pill brand
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(18.dp))
                .background(HudChip)
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Text(
                text = "ALYA ARENA",
                color = HudTextDark,
                fontSize = 15.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp
            )
        }

        StatusChip(icon = Icons.Filled.BatteryFull, label = "Estimate", value = "9h 40m")

        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(HudChip)
                .clickable(onClick = onAddGame),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Switch game", tint = HudTextDark)
        }
    }
}

@Composable
private fun StatusChip(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .background(HudChip)
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = label, tint = HudTextMuted, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = label, color = HudTextMuted, fontSize = 10.sp)
            Text(text = value, color = HudTextDark, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun BoosterCard(game: Game, onStart: () -> Unit) {
    Box {
        Column(
            modifier = Modifier
                .width(360.dp)
                .shadow(18.dp, RoundedCornerShape(28.dp), ambientColor = game.accent, spotColor = game.accent)
                .clip(RoundedCornerShape(28.dp))
                .background(HudCard)
                .border(1.dp, HudCardBorder, RoundedCornerShape(28.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(
                        Brush.verticalGradient(listOf(game.accent.copy(alpha = 0.35f), game.accent.copy(alpha = 0.1f)))
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = game.emoji, fontSize = 42.sp)
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = game.name,
                color = HudTextDark,
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 0.5.sp
            )
            Text(
                text = "READY TO BOOST",
                color = HudTextMuted,
                fontSize = 11.sp,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(HudTextDark)
                    .clickable(onClick = onStart)
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "START",
                    color = Color.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 3.sp
                )
            }
        }

        // "..." options button welded onto the card's top-right edge.
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 28.dp, end = 0.dp)
                .size(36.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(1.dp, HudCardBorder, CircleShape)
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.MoreVert, contentDescription = "Options", tint = HudTextDark, modifier = Modifier.size(18.dp))
        }
    }
}

@Composable
private fun GamePickerOverlay(
    selectedId: String,
    onPick: (Game) -> Unit,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xCC06070D))
            .clickable(onClick = onDismiss)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .width(340.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(HudCard)
                .clickable(enabled = false) { } // absorb clicks so they don't dismiss
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "SWITCH GAME",
                    color = HudTextDark,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 2.sp
                )
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "Close",
                    tint = HudTextMuted,
                    modifier = Modifier
                        .size(22.dp)
                        .clickable(onClick = onDismiss)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn {
                items(GameRepository.dummyGames) { game ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (game.id == selectedId) game.accent.copy(alpha = 0.15f) else Color.Transparent)
                            .clickable { onPick(game) }
                            .padding(horizontal = 12.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = game.emoji, fontSize = 22.sp)
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text(text = game.name, color = HudTextDark, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            Text(text = game.boostMode, color = HudTextMuted, fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}
