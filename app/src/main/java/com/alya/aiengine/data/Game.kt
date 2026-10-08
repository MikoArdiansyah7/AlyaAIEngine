package com.alya.aiengine.data

import androidx.compose.ui.graphics.Color
import com.alya.aiengine.ui.theme.NeonAmber
import com.alya.aiengine.ui.theme.NeonCyan
import com.alya.aiengine.ui.theme.NeonLime
import com.alya.aiengine.ui.theme.NeonMagenta
import com.alya.aiengine.ui.theme.NeonViolet

/**
 * Simple, modular game model. To add a new game later, just add a new entry
 * to [GameRepository.dummyGames] — no other screen code needs to change.
 *
 * [boostMode] is the label shown on the big FPS badge on the booster card.
 * It's a static "selected performance mode" label for now (stage 1 has no
 * real FPS monitoring yet) — not a live reading.
 */
data class Game(
    val id: String,
    val name: String,
    val emoji: String,
    val accent: Color,
    val boostMode: String = "120 FPS"
)

object GameRepository {
    val dummyGames: List<Game> = listOf(
        Game(id = "pubgm", name = "PUBG MOBILE", emoji = "🎯", accent = NeonAmber, boostMode = "120 FPS"),
        Game(id = "mlbb", name = "MOBILE LEGENDS", emoji = "⚔\uFE0F", accent = NeonViolet, boostMode = "144 FPS"),
        Game(id = "codm", name = "CALL OF DUTY", emoji = "\uD83D\uDD2B", accent = NeonCyan, boostMode = "90 FPS"),
        Game(id = "ffire", name = "FREE FIRE", emoji = "\uD83D\uDD25", accent = NeonMagenta, boostMode = "60 FPS"),
        Game(id = "genshin", name = "GENSHIN IMPACT", emoji = "\u2728", accent = NeonLime, boostMode = "60 FPS")
    )
}
