package com.mediovyn.player.feature.videopicker.screens

import com.mediovyn.player.core.model.Folder

sealed interface MediaState {
    data object Loading : MediaState
    data class Success(val data: Folder?) : MediaState
}
