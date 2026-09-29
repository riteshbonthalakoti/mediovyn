package com.mediovyn.player.settings.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.mediovyn.player.settings.Setting
import com.mediovyn.player.settings.SettingsOutput
import com.mediovyn.player.settings.SettingsScreen
import kotlinx.serialization.Serializable

@Serializable
object SettingsRoute : NavKey

fun NavBackStack<NavKey>.navigateToSettings() {
    add(SettingsRoute)
}

fun EntryProviderScope<NavKey>.settingsEntry(onNavigateUp: () -> Unit, onItemClick: (Setting) -> Unit) {
    entry<SettingsRoute> {
        SettingsScreen(output = SettingsOutput(navigateUp = onNavigateUp, openSetting = onItemClick))
    }
}
