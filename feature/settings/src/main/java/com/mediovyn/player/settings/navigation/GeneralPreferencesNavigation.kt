package com.mediovyn.player.settings.navigation

import androidx.compose.runtime.SideEffect
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.mediovyn.player.settings.screens.general.GeneralPreferencesScreen
import com.mediovyn.player.settings.screens.general.GeneralPreferencesViewModel
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Serializable
object GeneralPreferencesRoute : NavKey

fun NavBackStack<NavKey>.navigateToGeneralPreferences() {
    add(GeneralPreferencesRoute)
}

fun EntryProviderScope<NavKey>.generalPreferencesEntry(onNavigateUp: () -> Unit) {
    entry<GeneralPreferencesRoute> {
        val output = GeneralPreferencesViewModel.Output(
            navigateUp = onNavigateUp,
        )
        val viewModel = koinViewModel<GeneralPreferencesViewModel>(
            parameters = { parametersOf(output) },
        )
        SideEffect { viewModel.output = output }
        GeneralPreferencesScreen(viewModel = viewModel)
    }
}
