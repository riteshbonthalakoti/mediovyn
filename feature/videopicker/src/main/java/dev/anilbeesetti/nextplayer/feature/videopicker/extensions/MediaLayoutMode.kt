package com.mediovyn.player.feature.videopicker.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mediovyn.player.core.model.MediaLayoutMode
import com.mediovyn.player.core.ui.R

@Composable
fun MediaLayoutMode.name(): String {
    return when (this) {
        MediaLayoutMode.LIST -> stringResource(id = R.string.list)
        MediaLayoutMode.GRID -> stringResource(id = R.string.grid)
    }
}
