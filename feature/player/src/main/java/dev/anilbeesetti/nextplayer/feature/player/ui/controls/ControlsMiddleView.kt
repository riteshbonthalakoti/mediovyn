package com.mediovyn.player.feature.player.ui.controls

import androidx.annotation.OptIn
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import com.mediovyn.player.core.ui.theme.MediovynTheme
import com.mediovyn.player.feature.player.buttons.NextButton
import com.mediovyn.player.feature.player.buttons.PlayPauseButton
import com.mediovyn.player.feature.player.buttons.PreviousButton
import com.mediovyn.player.feature.player.ui.preview.rememberPreviewPlayer

@OptIn(UnstableApi::class)
@Composable
fun ControlsMiddleView(
    modifier: Modifier = Modifier,
    player: Player?,
) {
    val playPauseFocusRequester = remember { FocusRequester() }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .focusProperties {
                onEnter = { playPauseFocusRequester.requestFocus() }
            }
            .focusGroup(),
        horizontalArrangement = Arrangement.spacedBy(40.dp, alignment = Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PreviousButton(player = player)
        PlayPauseButton(
            player = player,
            modifier = Modifier.focusRequester(playPauseFocusRequester),
        )
        NextButton(player = player)
    }
}

@OptIn(UnstableApi::class)
@Preview
@Composable
private fun ControlsMiddleViewPreview() {
    MediovynTheme(darkTheme = true) {
        Surface {
            ControlsMiddleView(
                player = rememberPreviewPlayer(),
            )
        }
    }
}
