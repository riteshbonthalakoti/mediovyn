package com.mediovyn.player.settings.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mediovyn.player.core.model.Font
import com.mediovyn.player.core.ui.R

@Composable
fun Font.name(): String {
    val stringRes = when (this) {
        Font.DEFAULT -> R.string.default_name
        Font.MONOSPACE -> R.string.monospace
        Font.SANS_SERIF -> R.string.sans_serif
        Font.SERIF -> R.string.serif
    }

    return stringResource(id = stringRes)
}
