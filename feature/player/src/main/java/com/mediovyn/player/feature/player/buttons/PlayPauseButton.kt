package com.mediovyn.player.feature.player.buttons

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.compose.state.rememberPlayPauseButtonState
import com.mediovyn.player.core.ui.R as coreUiR

@OptIn(UnstableApi::class)
@Composable
fun PlayPauseButton(
    modifier: Modifier = Modifier,
    player: Player?,
) {
    val state = rememberPlayPauseButtonState(player)
    val icon = when (state.showPlay) {
        true -> painterResource(coreUiR.drawable.ic_play)
        false -> painterResource(coreUiR.drawable.ic_pause)
    }
    val contentDescription = when (state.showPlay) {
        true -> stringResource(coreUiR.string.play_pause)
        false -> stringResource(coreUiR.string.play_pause)
    }

    Box(
        modifier = modifier
            .size(72.dp)
            .clip(CircleShape)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF7C3AED),
                        Color(0xFF06B6D4),
                        Color(0xCC4C1D95)
                    )
                )
            )
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        PlayerButton(
            modifier = Modifier.size(64.dp),
            enabled = state.isEnabled,
            onClick = state::onClick,
        ) {
            Icon(
                painter = icon,
                contentDescription = contentDescription,
                modifier = Modifier.size(40.dp),
                tint = Color.White
            )
        }
    }
}