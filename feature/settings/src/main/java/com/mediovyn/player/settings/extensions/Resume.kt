package com.mediovyn.player.settings.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mediovyn.player.core.model.Resume
import com.mediovyn.player.core.ui.R

@Composable
fun Resume.name(): String {
    val stringRes = when (this) {
        Resume.YES -> R.string.yes
        Resume.NO -> R.string.no
    }

    return stringResource(id = stringRes)
}
