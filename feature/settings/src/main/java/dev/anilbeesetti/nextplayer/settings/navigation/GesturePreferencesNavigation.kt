package com.mediovyn.player.settings.navigation

import androidx.compose.runtime.SideEffect
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.mediovyn.player.settings.screens.gesture.GesturePreferencesScreen
import com.mediovyn.player.settings.screens.gesture.GesturePreferencesViewModel
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Serializable
object GesturePreferencesRoute : NavKey

fun NavBackStack<NavKey>.navigateToGesturePreferences() {
    add(GesturePreferencesRoute)
}

fun EntryProviderScope<NavKey>.gesturePreferencesEntry(onNavigateUp: () -> Unit) {
    entry<GesturePreferencesRoute> {
        val output = GesturePreferencesViewModel.Output(
            navigateUp = onNavigateUp,
        )
        val viewModel = koinViewModel<GesturePreferencesViewModel>(
            parameters = { parametersOf(output) },
        )
        SideEffect { viewModel.output = output }
        GesturePreferencesScreen(viewModel = viewModel)
    }
}
