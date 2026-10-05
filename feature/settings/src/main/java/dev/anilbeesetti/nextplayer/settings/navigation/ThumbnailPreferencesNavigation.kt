package com.mediovyn.player.settings.navigation

import androidx.compose.runtime.SideEffect
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.mediovyn.player.settings.screens.thumbnail.ThumbnailPreferencesScreen
import com.mediovyn.player.settings.screens.thumbnail.ThumbnailPreferencesViewModel
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Serializable
object ThumbnailPreferencesRoute : NavKey

fun NavBackStack<NavKey>.navigateToThumbnailPreferencesScreen() {
    add(ThumbnailPreferencesRoute)
}

fun EntryProviderScope<NavKey>.thumbnailPreferencesEntry(onNavigateUp: () -> Unit) {
    entry<ThumbnailPreferencesRoute> {
        val output = ThumbnailPreferencesViewModel.Output(navigateUp = onNavigateUp)
        val viewModel = koinViewModel<ThumbnailPreferencesViewModel>(
            parameters = { parametersOf(output) },
        )
        SideEffect { viewModel.output = output }
        ThumbnailPreferencesScreen(viewModel = viewModel)
    }
}
