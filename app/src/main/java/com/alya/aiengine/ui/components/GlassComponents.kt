package com.alya.aiengine.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alya.aiengine.ui.theme.GlassFillDark
import com.alya.aiengine.ui.theme.GlassFillLight
import com.alya.aiengine.ui.theme.GlassStroke
import com.alya.aiengine.ui.theme.TextPrimary

/** A translucent, rounded "glass" panel so text stays legible over a busy video background. */
@Composable
fun GlassPanel(
    modifier: Modifier = Modifier,
    cornerRadius: Int = 24,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius.dp))
            .background(
                Brush.verticalGradient(listOf(GlassFillLight, GlassFillDark))
            )
            .border(1.dp, GlassStroke, RoundedCornerShape(cornerRadius.dp))
    ) {
        content()
    }
}

/**
 * A large gaming-style call-to-action button: gradient fill, soft neon glow,
 * and a subtle scale-down animation on press (matches the "smooth, not
 * excessive" animation guidance).
 */
@Composable
fun NeonButton(
    text: String,
    colors: List<Color>,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.96f else 1f,
        animationSpec = tween(120),
        label = "neonButtonScale"
    )

    Box(
        modifier = modifier
            .shadow(
                elevation = if (pressed) 8.dp else 20.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = colors.first(),
                spotColor = colors.first()
            )
            .clip(RoundedCornerShape(20.dp))
            .background(Brush.horizontalGradient(colors))
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 40.dp, vertical = 18.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color(0xFF06070D),
            fontSize = 20.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 2.sp
        )
    }
}

/** Small ghost/outline button used for secondary actions like Settings, Games, About, Back. */
@Composable
fun GhostButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(GlassFillLight)
            .border(1.dp, GlassStroke, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 22.dp, vertical = 10.dp)
    ) {
        Text(
            text = text,
            color = TextPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 1.sp
        )
    }
}
