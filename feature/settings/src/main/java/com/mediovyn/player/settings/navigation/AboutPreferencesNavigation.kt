package com.mediovyn.player.settings.navigation

import androidx.compose.runtime.SideEffect
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.mediovyn.player.settings.screens.about.AboutPreferencesScreen
import com.mediovyn.player.settings.screens.about.AboutPreferencesViewModel
import com.mediovyn.player.settings.screens.about.LibrariesScreen
import com.mediovyn.player.settings.screens.about.LibrariesViewModel
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Serializable
object AboutPreferencesRoute : NavKey

@Serializable
object LibrariesRoute : NavKey

fun NavBackStack<NavKey>.navigateToAboutPreferences() {
    add(AboutPreferencesRoute)
}

fun NavBackStack<NavKey>.navigateToLibraries() {
    add(LibrariesRoute)
}

fun EntryProviderScope<NavKey>.aboutPreferencesEntry(
    onLibrariesClick: () -> Unit,
    onNavigateUp: () -> Unit,
) {
    entry<AboutPreferencesRoute> {
        val output = AboutPreferencesViewModel.Output(navigateUp = onNavigateUp, openLibraries = onLibrariesClick)
        val viewModel = koinViewModel<AboutPreferencesViewModel>(
            parameters = { parametersOf(output) },
        )
        SideEffect { viewModel.output = output }
        AboutPreferencesScreen(viewModel = viewModel)
    }
}

fun EntryProviderScope<NavKey>.librariesEntry(onNavigateUp: () -> Unit) {
    entry<LibrariesRoute> {
        val output = LibrariesViewModel.Output(navigateUp = onNavigateUp)
        val viewModel = koinViewModel<LibrariesViewModel>(
            parameters = { parametersOf(output) },
        )
        SideEffect { viewModel.output = output }
        LibrariesScreen(viewModel = viewModel)
    }
}
